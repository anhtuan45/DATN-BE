package com.ws.masterserver.utils.constants.enums;

import com.ws.masterserver.utils.base.WsException;
import com.ws.masterserver.utils.common.StringUtils;
import com.ws.masterserver.utils.constants.WsCode;

/**
 * @author myname
 */
public enum DiscountStatusEnums {
    PENDING("Chưa áp dụng"),
    ACTIVE("Đang áp dụng"),
    DE_ACTIVE("Ngừng áp dụng");

    private String name;

    DiscountStatusEnums(String name) {
        this.name = name;
    }

    public static DiscountStatusEnums from(String text) {
        if (StringUtils.isNullOrEmpty(text)) {
            return null;
        }
        for (DiscountStatusEnums item : DiscountStatusEnums.values()) {
            if (item.name().equals(text)) {
                return item;
            }
        }
        throw new WsException(WsCode.STATUS_INVALID);
    }

    public String getName() {
        return name;
    }
}
