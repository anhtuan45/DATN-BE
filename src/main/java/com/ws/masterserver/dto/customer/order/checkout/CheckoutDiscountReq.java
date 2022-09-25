package com.ws.masterserver.dto.customer.order.checkout;

import lombok.Data;

@Data
public class CheckoutDiscountReq {
    private String shipPrice;
    private String shopPrice;
    private String discountCode;
}
