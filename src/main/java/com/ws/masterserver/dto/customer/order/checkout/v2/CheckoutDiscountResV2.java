package com.ws.masterserver.dto.customer.order.checkout.v2;

import com.ws.masterserver.dto.customer.cart.response.CartResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CheckoutDiscountResV2 {
    private List<CartResponse> cart;
    private Long shipDiscount;
    private Long saleDiscount;
    private Long saleTotal;
    private Long total;
    private Long ship;
}
