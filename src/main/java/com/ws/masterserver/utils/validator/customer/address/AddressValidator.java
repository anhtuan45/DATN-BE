package com.ws.masterserver.utils.validator.customer.address;

import com.ws.masterserver.dto.customer.address.AddressReq;
import com.ws.masterserver.utils.common.ValidatorUtils;


public class AddressValidator {

    private static final String NAME_OF_RECIPIENT = "Tên người nhận";
    private static final String PHONE = "Số điện thoại";
    private static final String PROVINCE_ID = "ID Tỉnh/Thành phố";
    private static final String PROVINCE_NAME = "Tên Tỉnh/Thành phố";
    private static final String DISTRICT_ID = "ID Quận/Huyện";
    private static final String DISTRICT_NAME = "Tên Quận/Huyện";
    private static final String WARD_CODE = "ID Xã/Phường";
    private static final String WARD_NAME = "Tên Xã/Phường";
    private static final String ADDRESS_DETAIL = "Địa chỉ chi tiết";

    public AddressValidator() {
    }

    public static void validCreateAddress(AddressReq req) {
        ValidatorUtils.validNullOrEmpty(NAME_OF_RECIPIENT,req.getNameOfRecipient());
        ValidatorUtils.validLength(NAME_OF_RECIPIENT, req.getNameOfRecipient(), 100, true);
        ValidatorUtils.validNullOrEmpty(PHONE,req.getPhoneNumber());
        ValidatorUtils.validPhone(PHONE, req.getPhoneNumber());
        ValidatorUtils.validNullOrEmpty(ADDRESS_DETAIL,req.getExact());
        ValidatorUtils.validLength(ADDRESS_DETAIL,req.getExact(), 200, true);
        ValidatorUtils.validNullOrEmpty(PROVINCE_NAME,req.getProvinceName());
        ValidatorUtils.validNullOrEmpty(PROVINCE_ID,req.getProvinceId());
        ValidatorUtils.validNullOrEmpty(DISTRICT_ID,req.getDistrictId());
        ValidatorUtils.validNullOrEmpty(DISTRICT_NAME,req.getDistrictName());
        ValidatorUtils.validNullOrEmpty(WARD_CODE,req.getWardCode());
        ValidatorUtils.validNullOrEmpty(WARD_NAME,req.getWardName());

    }
}
