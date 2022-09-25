package com.ws.masterserver.service;

import com.ws.masterserver.dto.admin.product.create_update.ProductDto;
import com.ws.masterserver.utils.base.rest.CurrentUser;

public interface AdminProductService {
    Object create(CurrentUser currentUser, ProductDto dto);

    Object changeStatus(CurrentUser currentUser, String id);

    Object delete(CurrentUser currentUser, String id);

    Object deleteOption(CurrentUser currentUser, String productId, String optionId);

    Object update(CurrentUser currentUser, ProductDto payload);
}
