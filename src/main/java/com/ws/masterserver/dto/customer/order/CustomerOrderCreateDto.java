package com.ws.masterserver.dto.customer.order;

import lombok.Data;

@Data
public class CustomerOrderCreateDto {
    //address
    private String wardId;
    private String fullAddress;
    private Boolean isDefault;

    //cart


}
