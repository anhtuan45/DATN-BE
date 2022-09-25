package com.ws.masterserver.dto.customer.order;


import com.ws.masterserver.dto.customer.cart.response.CartResponse;
import com.ws.masterserver.utils.constants.enums.PaymentEnums;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequest {
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

//

    private String nameOfRecipient;
    private String phoneNumber;
    private String exact;
    private String provinceId;
    private String provinceName;
    private String districtId;
    private String districtName;
    private String wardCode;
    private String wardName;

}
