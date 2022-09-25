package com.ws.masterserver.service.impl;

import com.ws.masterserver.dto.admin.category.detail.CategoryDetailRes;
import com.ws.masterserver.dto.admin.discount.no_page.ProductDto;
import com.ws.masterserver.entity.CategoryEntity;
import com.ws.masterserver.service.AdminCategoryDetailService;
import com.ws.masterserver.utils.base.WsException;
import com.ws.masterserver.utils.base.WsRepository;
import com.ws.masterserver.utils.base.rest.CurrentUser;
import com.ws.masterserver.utils.common.ListUtils;
import com.ws.masterserver.utils.constants.WsCode;
import com.ws.masterserver.utils.validator.auth.AuthValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@SuppressWarnings("deprecation")
public class AdminCategoryDetailServiceImpl implements AdminCategoryDetailService {

    private final WsRepository repository;

    @Override
    public Object detail(CurrentUser currentUser, String id) {
        AuthValidator.checkAdminAndStaff(currentUser);
        CategoryEntity category = repository.categoryRepository.findById(id).orElseThrow(() -> new WsException(WsCode.ERROR_NOT_FOUND));
        return CategoryDetailRes.builder()
                .id(category.getId())
                .name(category.getName())
                .des(category.getDes())
                .products(repository.productRepository.findByCategoryIdAndActive(category.getId(), true)
                        .stream()
                        .map(o -> ProductDto.builder()
                                .id(o.getId())
                                .name(o.getName())
                                .build()))
                .typeId(category.getTypeId())
                .build();
    }
}
