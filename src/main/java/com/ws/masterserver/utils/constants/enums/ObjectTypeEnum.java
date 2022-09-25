package com.ws.masterserver.utils.constants.enums;

/**
 * @author myname
 */
public enum ObjectTypeEnum {
    ORDER("order"),
    USER("user");

    String value;

    public String getValue() {
        return value;
    }

    ObjectTypeEnum(String value) {
    }
}
