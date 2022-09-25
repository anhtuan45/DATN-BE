package com.ws.masterserver.dto.customer.order.checkin;

import lombok.Data;

import java.util.List;

@Data
public class CheckinDto {
    //address
    private String addressId;
    private String wardId;
    private String detail;
    private Boolean isDefault;

    //cart
    private List<ItemDto> items;

    //Note của khách hàng
    private String note;


}
