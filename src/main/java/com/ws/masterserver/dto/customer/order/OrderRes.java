package com.ws.masterserver.dto.customer.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRes {
    private String orderId;
    private String orderCode;
    private String createDate;
    private String address;
    private Long totalPrice;
    private Boolean payed;
    private String status;//trang thai van chuyen
}
