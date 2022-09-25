package com.ws.masterserver.utils.constants.enums;

import com.ws.masterserver.utils.base.WsException;
import com.ws.masterserver.utils.common.StringUtils;
import com.ws.masterserver.utils.constants.WsCode;

/**
 * @author myname
 */
public enum DiscountCustomerTypeEnums {
    ALL("Tất cả khách hàng"),
    GROUP("Nhóm khách hàng"),
    CUSTOMER("Khách hàng");

    private String name;

    DiscountCustomerTypeEnums(String name) {
        this.name = name;
    }

    public static DiscountCustomerTypeEnums from(String text) {
        if (StringUtils.isNullOrEmpty(text)) {
            return null;
        }
        for (DiscountCustomerTypeEnums item : DiscountCustomerTypeEnums.values()) {
            if (item.name().equals(text)) {
                return item;
            }
        }
        throw new WsException(WsCode.APPLY_CUSTOMER_TYPE_INVALID);
    }

    public String getName() {
        return name;
    }
}
