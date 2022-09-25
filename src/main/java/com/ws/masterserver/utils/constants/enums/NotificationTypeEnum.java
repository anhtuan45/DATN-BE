package com.ws.masterserver.utils.constants.enums;

import com.ws.masterserver.utils.common.StringUtils;

/**
 * @author myname
 */
public enum NotificationTypeEnum {
    NORMAL("icon-circle bg-primary", "fas fa-file-alt text-white"),
    SUCCESS("icon-circle bg-success", "fas fa-donate text-white"),
    WARNING("icon-circle bg-warning", "fas fa-exclamation-triangle text-white");

    private String div;
    private String icon;

    NotificationTypeEnum(String div, String icon) {
        this.div = div;
        this.icon = icon;
    }

    public static NotificationTypeEnum from(String type) {
        if (StringUtils.isNullOrEmpty(type)) {
            return null;
        }
        for (NotificationTypeEnum item : NotificationTypeEnum.values()) {
            if (type.equals(item.name())) {
                return item;
            }
        }
        return null;
    }

    public String getDiv() {
        return div;
    }

    public String getIcon() {
        return icon;
    }
}
