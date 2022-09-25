package com.ws.masterserver.utils.constants.enums;

public enum RoleEnum {
    /**
     * Danh sách quyền
     */
    ROLE_ADMIN("Admin"),//chủ hệ thống
    ROLE_STAFF("Nhân viên"),//nhân viên
    ROLE_CUSTOMER("Khách hàng"),//khách hàng
    ROLE_GUEST(""),
    ;

    private String name;

    RoleEnum(String name) {
        this.name = name;
    }

    public static RoleEnum from(String text) {
        for (RoleEnum item : RoleEnum.values()) {
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
