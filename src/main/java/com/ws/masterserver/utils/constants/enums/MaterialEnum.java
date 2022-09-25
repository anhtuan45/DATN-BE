package com.ws.masterserver.utils.constants.enums;

public enum MaterialEnum {
    MTR01("Vải"),
    MTR02("Cotton"),
    MTR03("Ka-ki")
    ;

    private final String name;

    MaterialEnum(String name) {

        this.name = name;
    }

    public String getName() {
        return name;
    }
}
