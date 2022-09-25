package com.ws.masterserver.dto.customer.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetailResponse {

    private String nameOfRecipient;
    private String phoneNumber;
    private String note;
    private String orderCode;
    private String createDate;
    private String totalPrice;
    private String shipPrice;
    private String shipAddress;
    private String paymentMethod;
    private boolean payed;
    private String status;
    private String statusOrder;
    private String totalDiscount;

    private List<ProductInOrderDetail> product;

}
