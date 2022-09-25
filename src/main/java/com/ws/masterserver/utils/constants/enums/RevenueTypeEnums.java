package com.ws.masterserver.utils.constants.enums;

import com.ws.masterserver.utils.common.StringUtils;

/**
 * @author myname
 */
public enum RevenueTypeEnums {

    DAY_OF_WEEK("Ngày trong tuần"),
    DAY_OF_MONTH("Ngày trong tháng"),
    WEEK_OF_MONTH("Tuần trong tháng"),
    MONTH_OF_YEAR("Tháng trong năm"),
    YEAR_BY_YEAR("Theo năm")
    ;

    private String name;

    RevenueTypeEnums(String name) {
        this.name = name;
    }

    public static RevenueTypeEnums from(String type) {
        if (StringUtils.isNullOrEmpty(type)) {
            return null;
        }
        for (RevenueTypeEnums item : RevenueTypeEnums.values()) {
            if (type.equals(item.name())) {
                return item;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }
}
