package com.ws.masterserver.service.impl;

import com.ws.masterserver.dto.admin.category.CategoryDto;
import com.ws.masterserver.dto.admin.category.CategoryReq;
import com.ws.masterserver.dto.admin.category.CategoryRes;
import com.ws.masterserver.entity.CategoryEntity;
import com.ws.masterserver.entity.ProductEntity;
import com.ws.masterserver.entity.TypeEntity;
import com.ws.masterserver.service.CategoryService;
import com.ws.masterserver.utils.base.WsException;
import com.ws.masterserver.utils.base.WsRepository;
import com.ws.masterserver.utils.base.rest.CurrentUser;
import com.ws.masterserver.utils.base.rest.PageData;
import com.ws.masterserver.utils.base.rest.ResData;
import com.ws.masterserver.utils.common.*;
import com.ws.masterserver.utils.constants.WsCode;
import com.ws.masterserver.utils.validator.auth.AuthValidator;
import com.ws.masterserver.utils.validator.admin.category.CategoryValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@SuppressWarnings("unchecked")
public class CategoryServiceImpl implements CategoryService {
    private final WsRepository repository;

    @Override
    public PageData<CategoryRes> search(CurrentUser currentUser, CategoryReq req) {
        AuthValidator.checkAdminAndStaff(currentUser);
        if (StringUtils.isNullOrEmpty(req.getTextSearch())) {
            req.setTextSearch("");
        }
        String textSearch = req.getTextSearch().trim().toUpperCase(Locale.ROOT);
        Pageable pageable = PageableUtils.getPageable(req.getPageReq());
        Page<CategoryEntity> categoryPage = repository.categoryRepository.search(textSearch, req.getStatus(), req.getTypeId(), pageable);

        return PageData.setResult(categoryPage.getContent().stream().map(o -> {
                    TypeEntity typeEntity = repository.typeRepository.findById(o.getTypeId()).orElse(null);
                    return CategoryRes.builder()
                            .id(o.getId())
                            .name(o.getName())
                            .des(o.getDes())
                            .active(o.getActive())
                            .activeName(o.getActive() ? "Hoạt động" : "Ngừng hoạt động")
                            .activeClazz(o.getActive() ? "success" : "danger")
                            .createdDate(o.getCreatedDate())
                            .createdDateFmt(o.getCreatedDate() == null ? null : DateUtils.toStr(o.getCreatedDate(), DateUtils.F_DDMMYYYY))
                            .productNumber(repository.productRepository.countByCategoryId(o.getId()))
                            .typeName(null == typeEntity ? null : typeEntity.getName())
                            .build();
                }).collect(Collectors.toList()),
                categoryPage.getNumber(),
                categoryPage.getSize(),
                categoryPage.getTotalElements());
    }

    @Override
    @Transactional
    public ResData<String> create(CurrentUser currentUser, CategoryDto dto) {
        AuthValidator.checkAdminAndStaff(currentUser);
        CategoryValidator.validCreate(dto);
        if (Boolean.TRUE.equals(repository.categoryRepository.existsByNameIgnoreCaseAndActive(dto.getName().trim(), Boolean.TRUE))) {
            throw new WsException(WsCode.CATEGORY_EXISTS_NAME);
        }
        TypeEntity typeEntity = repository.typeRepository.findById(dto.getTypeId()).orElse(null);
        if (typeEntity == null) {
            throw new WsException(WsCode.TYPE_NOT_FOUND);
        }
        String name = dto.getName().replaceAll("  ", " ");
        CategoryEntity category = CategoryEntity.builder()
                .id(UidUtils.generateUid())
                .name(name)
                .des(dto.getDes().trim())
                .active(Boolean.TRUE)
                .typeId(typeEntity.getId())
                .slug(SlugUtils.generateSlug(name, typeEntity.getName()))
                .build();

        log.info("create() category before save: {}", JsonUtils.toJson(category));
        repository.categoryRepository.save(category);
        log.info("create() category after save: {}", JsonUtils.toJson(category));

        dto.getProductIds().forEach(productId -> {
            ProductEntity product = repository.productRepository.findByIdAndActive(productId, true);
            log.info("create() product found before save: {}", JsonUtils.toJson(product));
            if (product != null) {
                product.setCategoryId(category.getId())
                        .setUpdatedBy(currentUser.getId())
                        .setUpdatedDate(new Date());
                repository.productRepository.save(product);
                log.info("create() product found after save: {}", JsonUtils.toJson(product));
            }
        });

        return new ResData<>(category.getId(), WsCode.OK);
    }

    @Override
    @Transactional
    public ResData<String> delete(CurrentUser currentUser, String id) {
        AuthValidator.checkAdmin(currentUser);
//        if (Boolean.FALSE.equals(repository.categoryRepository.existsById(id))) {
//            throw new WsException(WsCode.CATEGORY_NOT_FOUND);
//        }
        CategoryEntity category = repository.categoryRepository.findById(id).orElse(null);
        if (category == null) {
            throw new WsException(WsCode.CATEGORY_NOT_FOUND);
        }
        repository.categoryRepository.delete(category);
//        category.setActive(Boolean.FALSE);
//        repository.categoryRepository.save(category);
        List<ProductEntity> products = repository.productRepository.findByCategoryIdAndActive(category.getId(), Boolean.TRUE);
        if (Boolean.FALSE.equals(products.isEmpty())) {
            products.forEach(product -> {
                product.setCategoryId(null)
                        .setUpdatedBy(currentUser.getId())
                        .setUpdatedDate(new Date());
                repository.productRepository.save(product);
            });
        }
        return new ResData<>(category.getId(), WsCode.OK);
    }

    @Override
    @Transactional
    public Object update(CurrentUser currentUser, CategoryDto dto) {
        AuthValidator.checkAdminAndStaff(currentUser);
        CategoryEntity category = repository.categoryRepository.findByIdAndActive(dto.getId(), Boolean.TRUE);
        if (category == null) {
            throw new WsException(WsCode.ERROR_NOT_FOUND);
        }
        CategoryValidator.validCreate(dto);
        if (repository.categoryRepository.existsByNameAndActiveAndIdNot(dto.getName().trim(), Boolean.TRUE, dto.getId())) {
            throw new WsException(WsCode.CATEGORY_EXISTS_NAME);
        }
        TypeEntity typeEntity = repository.typeRepository.findById(dto.getTypeId()).orElse(null);
        if (typeEntity == null) {
            throw new WsException(WsCode.TYPE_NOT_FOUND);
        }
        String name = dto.getName().replaceAll("  ", " ");
        category.setName(dto.getName().trim())
                .setDes(dto.getDes().trim())
                .setTypeId(typeEntity.getId())
                .setUpdatedBy(currentUser.getId())
                .setUpdatedDate(new Date())
                .setSlug(SlugUtils.generateSlug(name, typeEntity.getName()));
        log.info("create() category before save: {}", JsonUtils.toJson(category));
        repository.categoryRepository.save(category);
        log.info("create() category after save: {}", JsonUtils.toJson(category));
        repository.productRepository.findByCategoryIdAndActive(category.getId(), true).stream()
                .forEach(product -> {
                    product.setCategoryId("")
                            .setUpdatedBy(currentUser.getId())
                            .setUpdatedDate(new Date());
                    repository.productRepository.save(product);
                });
        dto.getProductIds().forEach(productId -> {
            ProductEntity product = repository.productRepository.findByIdAndActive(productId, true);
            log.info("create() product found before save: {}", JsonUtils.toJson(product));
            if (product != null || !category.getId().equals(product.getCategoryId())) {
                product.setCategoryId(category.getId())
                        .setUpdatedBy(currentUser.getId())
                        .setUpdatedDate(new Date());
                repository.productRepository.save(product);
                log.info("create() product found after save: {}", JsonUtils.toJson(product));
            }
        });

        return category.getId();
    }

    @Override
    public ResData<CategoryRes> detail(CurrentUser currentUser, String id) {
        AuthValidator.checkAdmin(currentUser);
        CategoryEntity category = repository.categoryRepository.findByIdAndActive(id, true);
        if (null == category) {
            throw new WsException(WsCode.ERROR_NOT_FOUND);
        }
        return ResData.ok(CategoryRes.builder()
                .id(category.getId())
                .name(category.getName())
                .des(category.getDes())
                .image(category.getImage())
                .active(category.getActive())
                .createdDate(category.getCreatedDate())
                .createdDateFmt(category.getCreatedDate() == null ? null : DateUtils.toStr(category.getCreatedDate(), DateUtils.F_DDMMYYYY))
                .productNumber(repository.productRepository.countByCategoryId(category.getId()))
                .build());
    }

    @Override
    public Object changeStatus(CurrentUser currentUser, String id) {
        AuthValidator.checkAdmin(currentUser);
        CategoryEntity category = repository.categoryRepository.findById(id).orElse(null);
        if (category == null) {
            throw new WsException(WsCode.ERROR_NOT_FOUND);
        }

        Boolean newActive = !category.getActive();
        category.setActive(newActive);
        log.info("changeStatus() category before save: {}", JsonUtils.toJson(category));
        repository.categoryRepository.save(category);
        log.info("changeStatus() category after save: {}", JsonUtils.toJson(category));
        List<ProductEntity> products = repository.productRepository.findByCategoryId(category.getId());
        log.info("changeStatus() found products: {}", JsonUtils.toJson(products));
        if (!products.isEmpty()) {
            products.forEach(product -> {
                product.setActive(newActive)
                        .setUpdatedBy(currentUser.getId())
                        .setUpdatedDate(new Date());
                repository.productRepository.save(product);
            });
        }
        log.info("changeStatus() products after save: {}", JsonUtils.toJson(products));
        return ResData.ok(category.getId());
    }

    @Override
    public Object getListCategoryAndSlug() {

        List<com.ws.masterserver.dto.customer.category.CategoryRes> categoryRes = repository.categoryRepository.findAllCategoryAndSlug();
        return new ResData<>(categoryRes,WsCode.OK);
    }
}
