package com.ws.masterserver.utils.constants.enums;

public enum SizeEnum {
    S("S"),
    M("M"),
    L("L"),
    XL1("XL"),
    XL2("XXL"),
    XL3("XXXL");

    private final String name;

    SizeEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
