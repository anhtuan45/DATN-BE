package com.ws.masterserver.utils.constants.enums;

/**
 * @author myname
 */
public enum CustomerTypeEnums {
    NEW_CUSTOMER("Khách hàng mới"),
    VIP("Khách hàng VIP")
    ;

    private String name;

    CustomerTypeEnums(String name) {
        this.name = name;
    }

    public static CustomerTypeEnums from(String text) {
        for (CustomerTypeEnums item : CustomerTypeEnums.values()) {
            if (item.name().equals(text)) {
                return item;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }
}
