package com.ws.masterserver.utils.constants.enums;

/**
 * @author myname
 */
public enum PromotionTypeEnum {

    /**
     * @code TYPE1
     * @tittle SHIP
     * @name Vận chuyển
     * */
    TYPE1("SHIP", "Vận chuyển"),

    /**
     * @code TYPE2
     * @tittle SHOP
     * @name Mua sắm
     * */
    TYPE2("SHOP", "Mua sắm")
        ;

    private final String title;
    private final String name;

    PromotionTypeEnum(String id, String name) {
        this.title = id;
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public String getName() {
        return name;
    }
}
