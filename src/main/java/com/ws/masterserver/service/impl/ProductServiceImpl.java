package com.ws.masterserver.service.impl;

import com.ws.masterserver.dto.customer.product.ProductBestSeller;
import com.ws.masterserver.dto.customer.product.ProductRelatedRes;
import com.ws.masterserver.dto.customer.product.ProductReq;
import com.ws.masterserver.dto.admin.product.ProductDetailResponse;
import com.ws.masterserver.dto.admin.product_option.ProductOptionResponse;
import com.ws.masterserver.dto.customer.product.ProductResponse;
import com.ws.masterserver.dto.customer.review.response.ReviewResponse;
import com.ws.masterserver.entity.*;
import com.ws.masterserver.service.ProductService;
import com.ws.masterserver.utils.base.WsRepository;
import com.ws.masterserver.utils.base.rest.PageData;
import com.ws.masterserver.utils.common.DateUtils;
import com.ws.masterserver.utils.common.MoneyUtils;
import com.ws.masterserver.utils.constants.WsCode;
import com.ws.masterserver.utils.constants.WsConst;
import com.ws.masterserver.utils.base.rest.ResData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@SuppressWarnings("deprecation")
public class ProductServiceImpl implements ProductService {

    private final WsRepository repository;


    @Override
    @Transactional
    public ResData<ProductDetailResponse> getProductDetail(String id) {

        ProductDetailResponse response = new ProductDetailResponse();

        ProductEntity product = repository.productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format(WsConst.Messages.NOT_FOUND, WsConst.Nouns.CATEGORY_VI)));

        response.setProductId(product.getId());
        response.setProductName(product.getName());
        response.setDescription(product.getDes());

        MaterialEntity material = repository.materialRepository.findById(product.getMaterialId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format(WsConst.Messages.NOT_FOUND, WsConst.Nouns.MATERIAL_VI)));

        response.setMaterial(material.getName());

        CategoryEntity category = repository.categoryRepository.findById(product.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Not found category with id = " + id));
        response.setCategoryName(category.getName());



        List<ProductOptionEntity> productOptions = repository.productOptionRepository.findByProductId(id);

        response.setProductOptions(
                productOptions.stream().map(option -> {

                    ProductOptionResponse productOptionResponse = new ProductOptionResponse();
                    productOptionResponse.setImage(option.getImage());

                    ColorEntity color = repository.colorRepository.findById(option.getColorId())
                            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format(WsConst.Messages.NOT_FOUND, WsConst.Nouns.COLOR_VI)));

                    productOptionResponse.setColorName(color.getName());
                    productOptionResponse.setId(option.getId());
                    productOptionResponse.setQty(option.getQty());
                    productOptionResponse.setSizeId(option.getSizeId());

                    SizeEntity size = repository.sizeRepository.findById(option.getSizeId())
                            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format(WsConst.Messages.NOT_FOUND, WsConst.Nouns.SIZE_VI)));

                    productOptionResponse.setSizeName(size.getName());
                    productOptionResponse.setImage(option.getImage());
                    productOptionResponse.setPrice(MoneyUtils.formatV2(option.getPrice()));

                    return productOptionResponse;

                }).collect(Collectors.toList())
        );

        Integer countRating = repository.reviewRepository.countRatingActive(product.getId());
        response.setCountRating(countRating);

        Float avgRating = repository.reviewRepository.avgRating(product.getId());
        response.setAvgRating(avgRating);

        List<ReviewEntity> listReview = repository.reviewRepository.findByProductIdAndActive(id,Boolean.TRUE);

        response.setReview(
                listReview.stream().map(review -> {
                    ReviewResponse reviewResponse = new ReviewResponse();
                    String userName = repository.reviewRepository.getUserNameReview(review.getUserId(),review.getProductId());

                    reviewResponse.setReviewId(review.getId());
                    reviewResponse.setUserName(userName);
                    reviewResponse.setActive(review.getActive());
                    reviewResponse.setContent(review.getContent());
                    reviewResponse.setRating(review.getRating());
                    reviewResponse.setCreatedDate(DateUtils.parseDateToStr(DateUtils.DATE_TIME_FORMAT_VI,review.getCreatedDate()));

                    return reviewResponse;
                }).collect(Collectors.toList())
        );

        //increase +1 viewNumber
        log.info("getProductDetail increase viewNumber with ProductId: {}", id);
        repository.productRepository.increaseViewNumberByProductOptionId(id);

        return new ResData<>(response, WsCode.OK);
    }

    @Override
    public PageData<ProductResponse> search( ProductReq req) {
        //Nếu là khách hàng thì không cho search theo trạng thái
        return repository.productCustomRepository.search( req);
    }

    @Override
    public Object searchV2(com.ws.masterserver.dto.customer.product.search.ProductReq req) {
        return repository.productCustomRepository.searchV2(req);
    }

    @Override
    public ResData<List<ProductRelatedRes>> getRelatedProduct(String productId) {
        ProductEntity product = repository.productRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format(WsConst.Messages.NOT_FOUND, WsConst.Nouns.CATEGORY_VI)));
        List<ProductRelatedRes> productRelated = repository.productRepository.getProductRelated(product.getCategoryId());
        return new ResData<>(productRelated, WsCode.OK);
    }

    @Override
    public Object getProductBestSeller() {
        List<ProductBestSeller> product = repository.productRepository.testGet8Rows();
        return new ResData<>(product,WsCode.OK);
    }
}
