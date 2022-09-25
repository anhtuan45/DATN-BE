package com.ws.masterserver.utils.constants.enums;

import com.ws.masterserver.utils.base.WsException;
import com.ws.masterserver.utils.common.StringUtils;
import com.ws.masterserver.utils.constants.WsCode;

public enum DiscountTypeEnums {
    PERCENT("Theo phần trăm"),
    PRICE("Theo số tiền"),
    SHIP("Miễn phí vận chuyển");

    private String name;

    public String getName() {
        return name;
    }

    DiscountTypeEnums(String name) {
        this.name = name;
    }

    public static DiscountTypeEnums from(String text) {
        if (StringUtils.isNullOrEmpty(text)) {
            throw new WsException(WsCode.DISCOUNT_TYPE_INVALID);
        }
        for (DiscountTypeEnums item : DiscountTypeEnums.values()) {
            if (item.name().equals(text)) {
                return item;
            }
        }
        return null;
    }
}
