package com.ws.masterserver.service.impl;

import com.ws.masterserver.dto.admin.product.detail.OptionDto;
import com.ws.masterserver.dto.admin.product.detail.ProductDetailRes;
import com.ws.masterserver.dto.admin.product.detail.ReviewDto;
import com.ws.masterserver.dto.customer.product.ProductReq;
import com.ws.masterserver.entity.ProductEntity;
import com.ws.masterserver.service.AdminProductDetailService;
import com.ws.masterserver.utils.base.WsException;
import com.ws.masterserver.utils.base.WsRepository;
import com.ws.masterserver.utils.base.rest.CurrentUser;
import com.ws.masterserver.utils.common.*;
import com.ws.masterserver.utils.constants.WsCode;
import com.ws.masterserver.utils.constants.enums.RoleEnum;
import com.ws.masterserver.utils.validator.auth.AuthValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminProductDetailServiceImpl implements AdminProductDetailService {

    private final WsRepository repository;

    @Override
    public Object detail(CurrentUser currentUser, String id) {
        AuthValidator.checkAdminAndStaff(currentUser);
        ProductEntity product = repository.productRepository.findById(id).orElseThrow(() -> new WsException(WsCode.ERROR_NOT_FOUND));
        List<OptionDto> options = repository.productOptionRepository.findOptionsByProductId(id);
        List<ReviewDto> reviews = repository.reviewRepository.findReviewDtosByProductId(id)
                .stream().map(obj -> {
                    Date createdDate = obj.getCreatedDate();
                    obj.setCreatedDateFmt(createdDate == null ? null : DateUtils.toStr(createdDate, DateUtils.F_DDMMYYYYHHMM));
                    return obj;
                }).collect(Collectors.toList());
        return ProductDetailRes.builder()
                .id(id)
                .name(product.getName())
                .categoryId(product.getCategoryId())
                .des(product.getDes())
                .materialId(product.getMaterialId())
                .options(options)
                .reviews(reviews)
                .build();
    }

    @Override
    public Object search(CurrentUser currentUser, ProductReq req) {
        AuthValidator.checkAdminAndStaff(currentUser);
        log.info("search() start with req: {}", JsonUtils.toJson(req));
        return repository.productCustomRepository.search4Admin(req, repository);
    }
}
