package com.ws.masterserver.service.impl;

import com.ws.masterserver.dto.admin.product.create_update.OptionDto;
import com.ws.masterserver.dto.admin.product.create_update.ProductDto;
import com.ws.masterserver.entity.CategoryEntity;
import com.ws.masterserver.entity.ProductEntity;
import com.ws.masterserver.entity.ProductOptionEntity;
import com.ws.masterserver.proxy.CloudProxy;
import com.ws.masterserver.service.AdminProductService;
import com.ws.masterserver.utils.base.WsException;
import com.ws.masterserver.utils.base.WsRepository;
import com.ws.masterserver.utils.base.rest.CurrentUser;
import com.ws.masterserver.utils.common.*;
import com.ws.masterserver.utils.constants.WsCode;
import com.ws.masterserver.utils.validator.auth.AuthValidator;
import com.ws.masterserver.utils.validator.admin.product.AdminProductValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@EnableAsync
public class AdminProductServiceImpl implements AdminProductService {

    private final WsRepository repository;
    private final CloudProxy cloudProxy;

    @Override
    @Transactional
    public Object create(CurrentUser currentUser, ProductDto dto) {
        log.info("create() start with dto: {}", JsonUtils.toJson(dto));
        AuthValidator.checkAdminAndStaff(currentUser);
        AdminProductValidator.validCreate(dto, repository);
        Date now = new Date();
        String currentUserId = currentUser.getId();
        String productId = UidUtils.generateUid();
        ProductEntity product = ProductEntity.builder()
                .id(productId)
                .name(dto.getName().trim())
                .des(dto.getDes())
                .categoryId(dto.getCategoryId())
                .materialId(dto.getMaterialId())
                .active(true)
                .createdBy(currentUserId)
                .createdDate(now)
                .updatedBy(currentUserId)
                .updatedDate(now)
                .viewNumber(0L)
                .build();
        this.saveProduct(product);

        this.saveProductOptions(dto.getOptions(), productId);
        return productId;
    }

    @Override
    @Transactional
    public Object changeStatus(CurrentUser currentUser, String id) {
        AuthValidator.checkAdmin(currentUser);
        ProductEntity product = repository.productRepository.findById(id).orElseThrow(() -> new WsException(WsCode.ERROR_NOT_FOUND));
        if (!StringUtils.isNullOrEmpty(product.getCategoryId())) {
            CategoryEntity category = repository.categoryRepository.findById(product.getCategoryId()).orElseThrow(() -> new WsException(WsCode.ERROR_NOT_FOUND));
            if (!category.getActive()) {
                throw new WsException(WsCode.CAN_NOT_CHANGE_PRODUCT_STATUS_BECAUSE_CATEGORY_NOT_ACTIVE);
            }
        }
        product.setActive(!product.getActive())
                .setUpdatedBy(currentUser.getId())
                .setUpdatedDate(new Date());
        this.saveProduct(product);
        return product.getId();
    }

    @Override
    @Transactional
    public Object delete(CurrentUser currentUser, String id) {
        AuthValidator.checkAdmin(currentUser);
        ProductEntity product = repository.productRepository.findByIdAndActive(id, true);
        if (product == null) {
            throw new WsException(WsCode.ERROR_NOT_FOUND);
        }
        repository.productRepository.delete(product);
        List<ProductOptionEntity> options = repository.productOptionRepository.findByProductId(product.getId());
        if (!options.isEmpty()) {
            options.forEach(i -> {
                cloudProxy.delete(i.getImage());
                repository.productOptionRepository.delete(i);
            });
        }
        return true;
    }

    @Override
    @Transactional
    public Object deleteOption(CurrentUser currentUser, String productId, String optionId) {
        log.info("deleteOption() start with id: {}, optionId: {}", productId, optionId);
        AuthValidator.checkAdmin(currentUser);
        ProductEntity product = repository.productRepository.findById(productId).orElseThrow(() -> new WsException(WsCode.ERROR_NOT_FOUND));
        log.info("deleteOption() product: {}", JsonUtils.toJson(product));
        ProductOptionEntity productOption = repository.productOptionRepository.findByIdAndProductId(optionId, productId).orElseThrow(() -> new WsException(WsCode.ERROR_NOT_FOUND));
        log.info("deleteOption() productOption: {}", JsonUtils.toJson(productOption));
        String image = productOption.getImage();
        CompletableFuture<Void> cloudFuture = CompletableFuture.runAsync(() -> {
            if (!StringUtils.isNullOrEmpty(image)) {
                cloudProxy.delete(image);
            }
        });
        CompletableFuture<Void> optionFuture = CompletableFuture.runAsync(() -> {
            repository.productOptionRepository.delete(productOption);
        });

        try {
            CompletableFuture.allOf(cloudFuture, optionFuture).get();
        } catch (Exception e) {
            log.error("deleteOption() error: {}", e.getMessage());
            throw new WsException(WsCode.INTERNAL_SERVER);
        }
        return true;
    }

    @Override
    @Transactional
    public Object update(CurrentUser currentUser, ProductDto payload) {
        log.info("update() start with payload :{}", JsonUtils.toJson(payload));
        AuthValidator.checkAdminAndStaff(currentUser);
        if (!repository.productRepository.existsById(payload.getId())) {
            throw new WsException(WsCode.INTERNAL_SERVER);
        }
        AdminProductValidator.validUpdate(payload, repository);
        String productId = payload.getId();
        ProductEntity product = repository.productRepository.findByIdUpdate(productId);

        List<ProductOptionEntity> oldOptions = repository.productOptionRepository.findByProductId(productId);

        product.setCategoryId(payload.getCategoryId())
                .setName(payload.getName().trim())
                .setMaterialId(payload.getMaterialId())
                .setDes(payload.getDes().trim())
                .setUpdatedDate(new Date())
                .setUpdatedBy(currentUser.getId());
        this.saveProduct(product);
        this.saveProductOptions4Update(productId, oldOptions, payload.getOptions());
        return productId;
    }

    private void saveProductOptions4Update(String productId, List<ProductOptionEntity> oldOptions, List<OptionDto> options) {
        Map<String, OptionDto> map = options.stream().sorted(Comparator.comparing(OptionDto::getId, Comparator.nullsLast(Comparator.naturalOrder())))
                .collect(Collectors.toMap(
                        (dto -> dto.getSizeId().concat(dto.getColorId())),
                        dto -> dto,
                        (o1, o2) -> {
                            String id = o1.getId();
                            if (id == null) id = o2.getId();
                            long maxPrice = Math.max(Long.parseLong(o1.getPrice()), Long.parseLong(o2.getPrice()));
                            long totalQty = Long.parseLong(o1.getQty()) + Long.parseLong(o2.getQty());
                            this.removeImageAsync(o1.getImage());
                            return OptionDto.builder()
                                    .id(id)
                                    .colorId(o1.getColorId())
                                    .sizeId(o1.getSizeId())
                                    .price(String.valueOf(maxPrice))
                                    .qty(String.valueOf(totalQty))
                                    .image(o2.getImage())
                                    .build();
                        }));
        List<OptionDto> list4Update = new ArrayList<>(map.values());
        for (OptionDto option : list4Update) {
            String id = StringUtils.isNullOrEmpty(option.getId()) ? UidUtils.generateUid() : option.getId();
            repository.productOptionRepository.save(ProductOptionEntity.builder()
                    .id(id)
                    .image(option.getImage())
                    .productId(productId)
                    .qty(Long.parseLong(option.getQty()))
                    .price(Long.parseLong(option.getPrice()))
                    .sizeId(option.getSizeId())
                    .colorId(option.getColorId())
                    .build());
        }
        List<String> ids4Update = list4Update.stream().map(OptionDto::getId).collect(Collectors.toList());
        oldOptions.stream().filter(old -> !ids4Update.contains(old.getId())).forEach(old -> {
            removeImageAsync(old.getImage());
            repository.productOptionRepository.delete(old);
        });
    }

    @Async("threadPoolTaskExecutor")
    public void removeImageAsync(String image) {
        cloudProxy.delete(image);
    }

    private void saveProduct(ProductEntity product) {
        log.info("product before save: {}", JsonUtils.toJson(product));
        repository.productRepository.save(product);
        log.info("product after save: {}", JsonUtils.toJson(product));
    }

    private void saveProductOptions(List<OptionDto> options, String productId) {
        for (OptionDto option : options) {
            ProductOptionEntity po = ProductOptionEntity.builder()
                    .id(UidUtils.generateUid())
                    .productId(productId)
                    .colorId(option.getColorId())
                    .sizeId(option.getSizeId())
                    .qty(Long.valueOf(option.getQty()))
                    .price(Long.valueOf(option.getPrice()))
                    .image(option.getImage())
                    .build();
            log.info("po before save: {}", JsonUtils.toJson(po));
            repository.productOptionRepository.save(po);
            log.info("po after save: {}", JsonUtils.toJson(po));
        }
    }
}
