package com.ws.masterserver.utils.constants.enums;

/**
 * @author myname
 */
public enum BodyFigureTypeEnum {
    TYPE1("Thin", "Gầy"),
    TYPE2("Normal", "Bình thường"),
    TYPE3("Plump", "Đầy đặn");

    private String enTitle;
    private String viTitle;

    BodyFigureTypeEnum(String enTitle, String viTitle) {
        this.enTitle = enTitle;
        this.viTitle = viTitle;
    }

    public String getEnTitle() {
        return enTitle;
    }

    public String getViTitle() {
        return viTitle;
    }
}
