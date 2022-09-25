package com.ws.masterserver.dto.customer.address;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressRes {
    private String id;
    private String nameOfRecipient;
    private String exact;
    private String provinceId;
    private String provinceName;
    private String districtId;
    private String districtName;
    private String wardCode;
    private String wardName;
    private String combination;
    private String phoneNumber;
    private String userId;
    private Boolean isDefault;
    private Boolean active;
}
