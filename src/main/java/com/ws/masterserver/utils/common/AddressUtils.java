package com.ws.masterserver.utils.common;

import com.ws.masterserver.entity.AddressEntity;

/**
 * @author myname
 */
public class AddressUtils {
    public static String getCombination(AddressEntity address) {
        return String.format("%s, %s - %s - %s", address.getExact(), address.getWardName(), address.getDistrictName(), address.getProvinceName());
    }
}
