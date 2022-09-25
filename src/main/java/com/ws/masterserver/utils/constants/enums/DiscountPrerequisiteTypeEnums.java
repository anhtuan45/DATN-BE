package com.ws.masterserver.utils.constants.enums;

public enum DiscountPrerequisiteTypeEnums {
    NONE("Không"),
    TOTAL("Tổng giá trị đơn hàng tối thiểu"),
    QTY("Tổng số lượng sản phẩm được khuyến mãi tối thiếu");

    private String name;
    DiscountPrerequisiteTypeEnums(String name) {
        this.name = name;
    }

    public static DiscountPrerequisiteTypeEnums from(String text) {
        for (DiscountPrerequisiteTypeEnums item : DiscountPrerequisiteTypeEnums.values()) {
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
