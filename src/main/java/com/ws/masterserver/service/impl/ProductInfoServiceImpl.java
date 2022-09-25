package com.ws.masterserver.service.impl;

import com.ws.masterserver.dto.admin.discount.no_page.ProductDto;
import com.ws.masterserver.entity.ProductEntity;
import com.ws.masterserver.service.ProductInfoService;
import com.ws.masterserver.utils.base.WsRepository;
import com.ws.masterserver.utils.base.rest.CurrentUser;
import com.ws.masterserver.utils.base.rest.ResData;
import com.ws.masterserver.utils.validator.auth.AuthValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductInfoServiceImpl implements ProductInfoService {
    private final WsRepository repository;

    @Override
    public Object noPage(CurrentUser currentUser) {
        AuthValidator.checkLogin(currentUser);
        return ResData.ok(repository.productRepository.findAllOrderByName()
                .stream().map(ProductInfoServiceImpl::buildProductDtoFromEntity).collect(Collectors.toList()));
    }

    private static ProductDto buildProductDtoFromEntity(ProductEntity o) {
        return ProductDto.builder()
                .id(o.getId())
                .name(o.getName())
                .build();
    }

    @Override
    public Object noCategory(CurrentUser currentUser) {
        AuthValidator.checkAdminAndStaff(currentUser);
        return ResData.ok(repository.productRepository.find4_0Category()
                .stream().map(ProductInfoServiceImpl::buildProductDtoFromEntity).collect(Collectors.toList()));
    }

    @Override
    public Object noCategoryUpdate(CurrentUser currentUser, String id) {
        AuthValidator.checkAdminAndStaff(currentUser);
        if (!repository.categoryRepository.existsById(id)) {
            return new ArrayList<>();
        }
        List<ProductEntity> productEntities = repository.productRepository.find4_0CategoryUpdate(id);
        if (productEntities.isEmpty()) {
            return new ArrayList<>();
        }
        return productEntities.stream()
                .map(ProductInfoServiceImpl::buildProductDtoFromEntity)
                .collect(Collectors.toList());
    }


}
