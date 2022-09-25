package com.ws.masterserver.service;

import com.ws.masterserver.dto.customer.order.checkout.CheckoutDiscountReq;
import com.ws.masterserver.utils.base.rest.CurrentUser;

/**
 * @author myname
 */
public interface CheckoutDiscountService {
    Object check(CurrentUser currentUser, CheckoutDiscountReq payload);
}
