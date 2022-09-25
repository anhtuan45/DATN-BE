package com.ws.masterserver.utils.constants.enums;

/**
 */
public enum OrderTypeEnum {
    CASH("CASH", "Tiền mặt", "Thanh toán bằng tiền mặt"),
    VNP("VNPAY", "VN Pay", "Thanh toán qua VN-Pay"),
    ZLP("ZALOPAY", "Zalo Pay", "Thanh toán qua Zalo-Pay")
    ;

    private final String code;
    private final String title;
    private final String name;

    OrderTypeEnum(String code, String title, String name) {
        this.code = code;
        this.title = title;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }

    public String getName() {
        return name;
    }
}
