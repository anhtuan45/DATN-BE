package com.ws.masterserver.service;

import com.ws.masterserver.dto.customer.discount.DiscountReq;
import com.ws.masterserver.utils.base.rest.CurrentUser;

public interface DiscountService {
    Object getListMyDiscount(CurrentUser currentUser, DiscountReq payload);
}
