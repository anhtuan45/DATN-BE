package com.ws.masterserver.repository.custom;

import com.ws.masterserver.dto.customer.product.ProductReq;
import com.ws.masterserver.dto.customer.product.ProductResponse;
import com.ws.masterserver.utils.base.WsRepository;
import com.ws.masterserver.utils.base.rest.PageData;

public interface ProductCustomRepository {
//    PageData<ProductResponse> search(CurrentUser currentUser, ProductReq req);
    PageData<ProductResponse> search(ProductReq req);

    Object searchV2(com.ws.masterserver.dto.customer.product.search.ProductReq req);

    Object search4Admin(ProductReq req, WsRepository repository);
}
