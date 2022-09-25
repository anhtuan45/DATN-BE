package com.ws.masterserver.dto.customer.order;

import com.ws.masterserver.utils.constants.enums.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponse {
    private String orderId;
    private String orderCode;
    private String createDate;
    private String address;
    private String totalPrice;
    private Boolean payed;
    private StatusEnum status;//trang thai van chuyen
    private String statusValue;
}
