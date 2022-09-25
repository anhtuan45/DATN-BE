package com.ws.masterserver.utils.constants.enums;

import com.ws.masterserver.utils.common.StringUtils;

public enum StatusEnum {
    /**
     * Trạng thái đơn hàng
     */
    PENDING("Đang chờ xứ lý", ""),
    CANCELED("Khách hàng đã hủy", "danger"),
    REJECTED("Bị từ chối", "danger"),
    ACCEPTED("Được chấp nhận", "success"),
    SHIPPING("Đang giao hàng", "warning"),
    EXCHANGE("Đổi hàng", "warning"),
    REFUND("Trả hàng", "warning"),
    RECEIVED("Đã nhận được hàng", "success")

    ;

    private String name;
    private String clazz;


    StatusEnum(String name, String clazz) {
        this.name = name;
        this.clazz = clazz;
    }

    public static StatusEnum from(String text) {
        if (StringUtils.isNullOrEmpty(text)) {
            return null;
        }
        for (StatusEnum item : StatusEnum.values()) {
            if (item.name().equals(text)) {
                return item;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public String getClazz() {
        return clazz;
    }
}
