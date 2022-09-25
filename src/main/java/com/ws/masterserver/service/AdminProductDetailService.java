package com.ws.masterserver.service;

import com.ws.masterserver.dto.customer.product.ProductReq;
import com.ws.masterserver.utils.base.rest.CurrentUser;

public interface AdminProductDetailService {
    Object detail(CurrentUser currentUser, String id);
    Object search(CurrentUser currentUser, ProductReq req);
}
