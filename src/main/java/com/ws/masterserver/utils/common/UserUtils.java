package com.ws.masterserver.utils.common;

public class UserUtils {

    private UserUtils() {}

    public static String getCombination(String firstName, String lastName, Boolean gender) {
        if (Boolean.TRUE.equals(StringUtils.isNullOrEmpty(firstName)) ||
                Boolean.TRUE.equals(StringUtils.isNullOrEmpty(lastName))) return "";
        return (gender == null ? "" : "(" + (gender ? "Anh" : "Chị") + ") ") + (firstName + " " + lastName);
    }

    public static String getCombination(String combination, String firstName, String lastName, Boolean gender) {
        if (Boolean.TRUE.equals(StringUtils.isNullOrEmpty(combination))) {
            return getCombination(firstName, lastName, gender);
        }
        return getCombination(combination, gender);
    }

    public static String getCombination(String combination, Boolean gender) {
        return (gender == null ? "" : "(" + (gender ? "Anh" : "Chị") + ") ") + combination;
    }

    public static String getCustomerInfo(Boolean gender, String fullName) {
        return fullName;
    }
}
