package com.ws.masterserver.utils.constants.enums;

import com.ws.masterserver.utils.common.StringUtils;

public enum PaymentEnums {
    COD("COD","Thanh toán khi nhận hàng"),
    VNPAY("VNPAY","Thanh toán qua VN-Pay"),
    ATM("ATM","Thanh toán qua tài khoản ngân hàng"),
//    ZALOPAY("ZALOPAY","Thanh toán qua Zalo-Pay"),
    ;
    private final String code;
    private final String name;

    PaymentEnums(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static PaymentEnums from(String text) {
        if (StringUtils.isNullOrEmpty(text)) {
            return null;
        }
        for (PaymentEnums item : PaymentEnums.values()) {
            if (item.name().equals(text)) {
                return item;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
