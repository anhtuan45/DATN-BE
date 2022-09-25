package com.ws.masterserver.service;

import com.ws.masterserver.dto.customer.order.checkout.CheckoutDiscountReq;
import com.ws.masterserver.utils.base.rest.CurrentUser;

public interface CheckoutDiscountServiceV2 {
    Object verifyDiscountWhenCheckout(CurrentUser currentUser, CheckoutDiscountReq payload);
}
