package com.ws.masterserver.utils.constants.enums;

public enum DiscountSuperTypeEnums {
    NEW_CUSTOMER("Khách hàng mới");
    private final String name;

    DiscountSuperTypeEnums(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
