package com.ws.masterserver.utils.constants.enums;

public enum DiscountProvinceSelectionEnums {
    ALL("Tất cả"),
    SELECTION("Tỉnh.thành được chọn");

    private String name;

    DiscountProvinceSelectionEnums(String name) {
        this.name = name;
    }

    public static DiscountProvinceSelectionEnums from(String text) {
        for (DiscountProvinceSelectionEnums item : DiscountProvinceSelectionEnums.values()) {
            if (item.name().equals(text)) {
                return item;
            }
        }
        return null;
    }

    public String getName() {
        return this.name = name;
    }
}
