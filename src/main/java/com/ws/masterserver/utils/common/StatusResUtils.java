package com.ws.masterserver.utils.common;

import com.ws.masterserver.utils.base.dto.StatusDto;
import com.ws.masterserver.utils.constants.enums.DiscountStatusEnums;

/**
 * @author myname
 */
public class StatusResUtils {

    private static final String ACTIVE = "Hoạt động";
    private static final String DE_ACTIVE = "Ngưng hoạt động";
    private static final String ACTIVE_CLAZZ = "success";
    private static final String DE_ACTIVE_CLAZZ = "danger";
    private static final String DISCOUNT_ACTIVE = "Áp dụng";
    private static final String DISCOUNT_DE_ACTIVE = "Ngưng áp dụng";
    private static final String DISCOUNT_PENDING = "Chưa áp dụng";


    public static StatusDto getStatus(Boolean value) {
        if (value == null) {
            value = false;
        }
        return StatusDto.builder()
                .value(value)
                .title(value ? ACTIVE : DE_ACTIVE)
                .clazz(value ? ACTIVE_CLAZZ : DE_ACTIVE_CLAZZ)
                .build();
    }

    public static StatusDto getStatus4Discount(DiscountStatusEnums statusEnums) {
        if (statusEnums == null) {
            return null;
        }
        String clazz = "";
        switch (statusEnums) {
            case ACTIVE:
                clazz = ACTIVE_CLAZZ;
                break;
            case DE_ACTIVE:
                clazz = DE_ACTIVE_CLAZZ;
                break;
            case PENDING:
            default:
                break;
        }
        return StatusDto.builder()
                .value(true)
                .title(statusEnums.getName())
                .clazz(clazz)
                .build();
    }

}
