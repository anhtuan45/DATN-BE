package com.ws.masterserver.utils.validator;

import com.ws.masterserver.utils.base.WsException;
import com.ws.masterserver.utils.common.StringUtils;
import com.ws.masterserver.utils.constants.WsCode;
import com.ws.masterserver.utils.constants.WsConst;

import java.time.ZoneId;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateUtils {
    public ValidateUtils() {
    }

    public static void validBlank(String fieldName, String value) {
        if (StringUtils.isNullOrEmpty(value)) {
            throw new WsException(WsCode.REQUIRED_FIELD, String.format(WsCode.REQUIRED_FIELD.getMessage(), fieldName));
        }
    }

    public static void isValidLength(String fieldName, String value) {
        value.trim();
        if (value.length() <= 0 || value.length() >= 255) {
            throw new WsException(WsCode.INVALID_LENGTH, String.format(WsCode.INVALID_LENGTH.getMessage(), fieldName));
        }
    }

    public static void isValidMinLength(String fieldName, String value, int minLength) {
        value.trim();
        if (value.length() <= minLength || value.length() >= 255) {
            throw new WsException(WsCode.INVALID_LENGTH, String.format(WsCode.INVALID_LENGTH.getMessage(), fieldName));
        }
    }

    public static void isValidMaxLength(String fieldName, String value, int maxLength) {
        value.trim();
        if (value.length() <= 0 || value.length() >= maxLength) {
            throw new WsException(WsCode.INVALID_LENGTH, String.format(WsCode.INVALID_LENGTH.getMessage(), fieldName));
        }
    }

    public static void isValidRangeLength(String fieldName, String value, int minLength, int maxLength) {
        value.trim();
        if (value.length() <= minLength || value.length() >= maxLength) {
            throw new WsException(WsCode.INVALID_LENGTH, String.format(WsCode.INVALID_LENGTH.getMessage(), fieldName));
        }
    }


    public static Boolean isNegativeNumber(Object value) {
        if (value instanceof Integer) {
            return (Integer) value > 0;
        } else if (value instanceof Long) {
            return (Long) value > 0l;
        } else if (value instanceof Double) {
            return (Double) value > 0f;
        }
        return Boolean.FALSE;
    }

    public static boolean isValidEmail(final String email) {
        if (StringUtils.isNullOrEmpty(email)) {
            return false;
        }
        return ValidateUtils.isCheck(email, WsConst.Regex.EMAIL);
    }

    public static boolean isValidFullName(final String fullName) {
        if (StringUtils.isNullOrEmpty(fullName)) {
            return false;
        }
        return ValidateUtils.isCheck(fullName, WsConst.Regex.FULL_NAME);
    }

    public static boolean isValidPhoneNumber(final String phoneNumber) {
        if (StringUtils.isNullOrEmpty(phoneNumber)) {
            return false;
        }
        return ValidateUtils.isCheck(phoneNumber, WsConst.Regex.PHONE);
    }

    public static boolean isCheck(String value, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }

    public static boolean isValidPassword(String password) {
        return password.length() >= 6 && password.length() <= 20;
    }

    /**
     * Tuổi khách hàng phải từ 16 - 120
     */
    public static boolean isValidCustomerAge(Long dob) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(ZoneId.of(WsConst.Values.TIME_ZONE)));
        calendar.setTimeInMillis(dob);
        int year = calendar.get(Calendar.YEAR);
        return year >= WsConst.Values.CUSTOMER_AGE_MIN && year <= WsConst.Values.CUSTOMER_AGE_MAX;
    }
}
