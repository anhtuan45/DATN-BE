package com.ws.masterserver.service;

import com.ws.masterserver.dto.customer.product.ProductRelatedRes;
import com.ws.masterserver.dto.customer.product.ProductReq;
import com.ws.masterserver.dto.customer.product.ProductResponse;
import com.ws.masterserver.dto.admin.product.ProductDetailResponse;
import com.ws.masterserver.utils.base.rest.PageData;
import com.ws.masterserver.utils.base.rest.ResData;

import java.util.List;

public interface ProductService {
    ResData<ProductDetailResponse> getProductDetail(String id);
//    PageData<ProductResponse> search(CurrentUser currentUser, ProductReq productReq);
    PageData<ProductResponse> search( ProductReq productReq);

    Object searchV2(com.ws.masterserver.dto.customer.product.search.ProductReq req);

    ResData<List<ProductRelatedRes>> getRelatedProduct(String productId);

    Object getProductBestSeller();
}
