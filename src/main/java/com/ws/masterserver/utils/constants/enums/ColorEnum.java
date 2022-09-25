package com.ws.masterserver.utils.constants.enums;

public enum ColorEnum {
    WHITE("White", "Trắng", "#FFFFFF"),
    BLACK("Black", "Đen", "#000000"),
    GREEN("Green", "Xanh", "#0000FF"),
    GREY("Grey", "Xám", "#808080"),
    BLUE("Blue", "Lục", "#008000"),
    VIOLET("Violet", "Tím", "#800080"),
    RED("Red", "Đỏ", "#FF0000"),
            ;


    private final String enName;
    private final String viName;
    private final String hex;

    ColorEnum(String enName, String viName, String hex) {
        this.enName = enName;
        this.viName = viName;
        this.hex = hex;
    }

    public String getEnName() {
        return enName;
    }

    public String getViName() {
        return viName;
    }

    public String getHex() {
        return hex;
    }
}
