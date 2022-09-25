package com.ws.masterserver.dto.customer.order;

import com.ws.masterserver.dto.customer.cart.response.CartResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderReqq {
    private String addressId;
    private String note;
    private String paymentMethod;
    private String shipPrice;
    private String shipMethod;
    private Long total;//cart + ship - discount
    //private String coupon;
//    private String discountCode;
    private List<CartResponse> cart;
    private String discountCode;
    private Long shipPriceDiscount;
    private Long shopPriceDiscount;
    private Long shopPrice;//TotalPrice in cart order
}
