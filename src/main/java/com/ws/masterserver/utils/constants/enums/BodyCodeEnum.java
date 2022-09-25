package com.ws.masterserver.utils.constants.enums;


public enum BodyCodeEnum {

    TYPE1(1),
    TYPE2(2),
    TYPE3(3),
    TYPE4(4),
    TYPE5(5),
    TYPE6(6);


    private int value;

    BodyCodeEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
