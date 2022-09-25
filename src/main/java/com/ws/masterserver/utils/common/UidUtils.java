package com.ws.masterserver.utils.common;

import java.util.Locale;
import java.util.Random;
import java.util.UUID;

public class UidUtils {

    private UidUtils() {}

    public static String generateUid() {
        return UUID.randomUUID().toString();
    }

    public static String cleanUid(String uid) {
        return uid.replace("\"", "").trim();
    }

    public static String generateVoucher() {
        String uid = generateUid();
        return uid.substring(uid.lastIndexOf("-") + 1).substring(4).toUpperCase(Locale.ROOT);
    }

    public static String generateToken(Integer length) {
        if (length == null || length < 1) length = 6;
        String minSuffix = "1";
        String maxSuffix = "9";

        for (int i = 1; i < length; i++) {
            minSuffix += "0";
            maxSuffix += "9";
        }
        Integer min = Integer.valueOf(minSuffix);
        Integer max = Integer.valueOf(maxSuffix);
        Integer randomValue = new Random().nextInt(max - min) + min;
        return randomValue + "";
    }
}
