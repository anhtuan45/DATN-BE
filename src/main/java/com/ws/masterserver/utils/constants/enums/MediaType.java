package com.ws.masterserver.utils.constants.enums;

public enum MediaType {
    VIDEO("VDO", "Video", "Video"),
    IMAGE("IMG", "Image", "Ảnh")
    ;


    private final String code;
    private final String en;
    private final String vi;

    MediaType(String code, String en, String vi) {
        this.code = code;
        this.en = en;
        this.vi = vi;
    }

    public String getCode() {
        return code;
    }

    public String getEn() {
        return en;
    }

    public String getVi() {
        return vi;
    }
}
