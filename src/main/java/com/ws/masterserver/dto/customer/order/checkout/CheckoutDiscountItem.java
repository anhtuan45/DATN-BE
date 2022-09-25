package com.ws.masterserver.dto.customer.order.checkout;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class CheckoutDiscountItem {
    private BigDecimal raw;
    private BigDecimal discount;
    private BigDecimal total;
}
