package com.ws.masterserver.service;

import com.ws.masterserver.dto.admin.discount.search.DiscountRequest;
import com.ws.masterserver.utils.base.rest.CurrentUser;

public interface AdminDiscountDetailService {
    Object search(CurrentUser currentUser, DiscountRequest payload);
    Object detail(CurrentUser currentUser, String id);
}
