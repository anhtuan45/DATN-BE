package com.ws.masterserver.dto.customer.order.checkout;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CheckoutDiscountRes {
    private CheckoutDiscountItem shop;
    private CheckoutDiscountItem ship;
    private BigDecimal total;
}
