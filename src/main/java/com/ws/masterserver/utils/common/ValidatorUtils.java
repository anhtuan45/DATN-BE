package com.ws.masterserver.utils.common;

import com.ws.masterserver.utils.base.WsException;
import com.ws.masterserver.utils.constants.WsCode;
import com.ws.masterserver.utils.constants.WsMessage;
import com.ws.masterserver.utils.constants.enums.RoleEnum;
import com.ws.masterserver.utils.constants.field.DiscountFields;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * @author myname
 */
public class ValidatorUtils {
    private ValidatorUtils() {
        super();
    }

    private static final String NOT_BLANK = "Không được để trống ";
    private static final String INVALID = " không hợp lệ";
    private static final String MUST_LENGTH_LESS = " phải ít hơn ";
    private static final String MUST_LENGTH_MORE = " phải nhiều hơn ";
    private static final String CHARACTER = " ký tự";
    private static final String MUST_MORE = " phải lớn hơn ";
    private static final String MUST_LESS = " phải nhỏ hơn ";

    public static void validNullOrEmpty(String fieldName, String value) {
        if (StringUtils.isNullOrEmpty(value)) {
            throw new WsException(WsCode.BAD_REQUEST, NOT_BLANK + fieldName);
        }
    }

    public static void validNullOrEmpty(String fieldName, List<String> values) {
        if (values == null || values.isEmpty()) {
            throw new WsException(WsCode.BAD_REQUEST, NOT_BLANK + fieldName);
        }
        for (String value : values) {
            validNullOrEmpty(fieldName, value);
        }
    }

    public static void validNullOrEmptyStringList(String fieldName, List<String> values) {
        if (values == null || values.isEmpty()) {
            throw new WsException(WsCode.BAD_REQUEST, NOT_BLANK + fieldName);
        }
    }

    public static void validOnlyCharacterAndNumber(String fieldName, String value) {
        if (!StringUtils.isOnlyCharacterAndNumber(value)) {
            throw new WsException(WsCode.BAD_REQUEST, fieldName + INVALID);
        }
    }

    public static void validOnlyCharacterAndNumber(String fieldName, List<String> values) {
        if (values.isEmpty()) {
            return;
        }
        for (String value : values) {
            if (!StringUtils.isOnlyCharacterAndNumber(value)) {
                fieldName = fieldName.charAt(0) + fieldName.substring(1);
                throw new WsException(WsCode.BAD_REQUEST, fieldName + INVALID);
            }
        }
    }

    /**
     * valid do dai
     * type = true => valid max length
     */
    public static void validLength(String fieldName, String value, int length, boolean type) {
        if (type && value.length() > length) {
            throw new WsException(WsCode.BAD_REQUEST, fieldName + MUST_LENGTH_LESS + length + CHARACTER);
        }

        if (!type && value.length() < length) {
            throw new WsException(WsCode.BAD_REQUEST, fieldName + MUST_LENGTH_MORE + length + CHARACTER);
        }
    }

    public static void validLength(String fieldName, String value, int minLength, int maxLength) {
        if (value.length() < minLength) {
            throw new WsException(WsCode.BAD_REQUEST, fieldName + MUST_LENGTH_MORE + minLength + CHARACTER);
        }
        if (value.length() > maxLength) {
            throw new WsException(WsCode.BAD_REQUEST, fieldName + MUST_LENGTH_LESS + maxLength + CHARACTER);
        }
    }

    public static void validLength(String fieldName, List<String> values, int minLength, int maxLength) {
        if (values == null || values.isEmpty()) {
            return;
        }
        for (String value : values) {
            if (value.length() < minLength) {
                throw new WsException(WsCode.BAD_REQUEST, fieldName + MUST_LENGTH_MORE + minLength + CHARACTER);
            }
            if (value.length() > maxLength) {
                throw new WsException(WsCode.BAD_REQUEST, fieldName + MUST_LENGTH_LESS + maxLength + CHARACTER);
            }
        }
    }

    public static void validOnlyNumber(String fieldName, String value) {
        if (!StringUtils.isOnlyNumber(value)) {
            throw new WsException(WsCode.BAD_REQUEST, fieldName + INVALID);
        }
    }

    public static void validPrice(String fieldName, String value) {
        try {
            if (value.startsWith("0") || value.contains(" ")) {
                throw new WsException(WsCode.BAD_REQUEST, fieldName + INVALID);
            }
            Long price = Long.valueOf(value);
            if (price < 0) {
                throw new WsException(WsCode.BAD_REQUEST, fieldName + INVALID);
            }
        } catch (NumberFormatException e) {
            throw new WsException(WsCode.BAD_REQUEST, fieldName + INVALID);
        }
    }

    public static void validOnlyCharacter(String fieldName, String value) {
        if (!StringUtils.isOnlyCharacter(value)) {
            throw new WsException(WsCode.BAD_REQUEST, fieldName + INVALID);
        }
    }

    public static void validEmail(String fieldName, String value) {
        if (!StringUtils.isValidEmail(value)) {
            throw new WsException(WsCode.BAD_REQUEST, fieldName + INVALID);
        }
    }

    public static void validPhone(String fieldName, String value) {
        if (!StringUtils.isCheck(value, StringUtils.PHONE_NUMBER_REGEX)) {
            throw new WsException(WsCode.BAD_REQUEST, fieldName + INVALID);
        }
    }

    public static void validRole(String fieldName, String value) {
        validNullOrEmpty(fieldName, value);
        RoleEnum role = RoleEnum.valueOf(value);
        if (role == null) {
            throw new WsException(WsCode.BAD_REQUEST, fieldName + INVALID);
        }
    }

    public static void validBooleanType(String fieldName, Boolean value) {
        if (value == null) {
            throw new WsException(WsCode.BAD_REQUEST, fieldName + INVALID);
        }
    }

    public static void validAgeBetween(String fieldName, Date value, Integer minAge, Integer maxAge) {
        if (value == null) {
            throw new WsException(WsCode.BAD_REQUEST, NOT_BLANK + fieldName.toLowerCase());
        }
        LocalDate now = LocalDate.now();
        LocalDate dob = value.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        if (dob.compareTo(now) > 0) {
            throw new WsException(WsCode.DOB_NOT_MORE_NOW);
        }
        if (null != minAge) {
            now = now.minusYears(minAge);
            if (dob.compareTo(now) > 0) {
                throw new WsException(WsCode.AGE_MUST_MORE, WsCode.AGE_MUST_MORE.getMessage() + " " + minAge);
            }
        }
        if (null != maxAge) {
            now = now.minusYears(maxAge);
            if (dob.compareTo(now) < 0) {
                throw new WsException(WsCode.AGE_MUST_LESS, WsCode.AGE_MUST_LESS.getMessage() + " " + minAge);
            }
        }
    }

    public static void validNewPassNotSameOldPass(String password, String newPassword) {
        if (newPassword.equals(password)) {
            throw new WsException(WsCode.NEW_PASS_NOT_SAME_OLD);
        }
    }

    public static void validLongValueBetween(String fieldName, String value, Long minValue, Long maxValue) {
        Long percent = Long.valueOf(value);
        if (percent < minValue || percent > maxValue) {
            throw new WsException(WsCode.BAD_REQUEST, fieldName + " phải từ " + minValue + " đến " + maxValue);
        }
    }

    public static void validLongValueMustBeMore(String fieldName, String value, Long minValue) {
        Long percent = Long.valueOf(value);
        if (percent <= minValue) {
            throw new WsException(WsCode.BAD_REQUEST, fieldName + MUST_MORE + minValue);
        }
    }

    public static void validDateFormat(String fieldName, String value) {
        try {
            DateTimeFormatter sdf = DateTimeFormatter.ofPattern(DateUtils.F_DDMMYYYY);
            LocalDate localDate = LocalDate.parse(value, sdf);
        } catch (Exception e) {
            throw new WsException(WsCode.DATE_FORMAT_INVALID);
        }
    }

    public static void validTimeFormat(String fieldName, String value) {

    }

    public static void validDateMoreDateAndTimeMoreTime(String startDate, String startDateValue, String endDate, String endDateValue, String startTime, String startTimeValue, String endTime, String endTimeValue) {
    }

    public static void validNotContainSpace(String fieldName, String value) {
        if (StringUtils.isNullOrEmpty(value)) {
            return;
        }
        if (value.trim().contains(" ")) {
            throw new WsException(WsCode.BAD_REQUEST, fieldName + " " + WsCode.NOT_CONTAIN_SPACE.getMessage().toLowerCase(Locale.ROOT));
        }
    }

    public static void validNotMoreNow(String fieldName, String value) {
        if (DateUtils.toDate(value, DateUtils.F_DDMMYYYY).after(new Date())) {
            throw new WsException(WsCode.BAD_REQUEST, fieldName + " " + WsMessage.NOT_MORE_NOW.toLowerCase());
        }
    }

    public static void validAgeBetween(String fieldName, String value, int minAge, int maxAge) {
        try {
            Date dateValue = new SimpleDateFormat("dd/MM/yyyy").parse(value);
            validAgeBetween(fieldName, dateValue, minAge, maxAge);
        } catch (Exception e) {
            throw new WsException(WsCode.BAD_REQUEST, fieldName + INVALID);
        }
    }

    public static Date validDateTimeFormat(String fieldName, String value, String pattern) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            return sdf.parse(value);
        } catch (Exception e) {
            throw new WsException(WsCode.BAD_REQUEST, fieldName + INVALID);
        }
    }

    public static void validLongPriceValueBetween(String fieldName, String value, long minValue, long maxValue) {
        BigDecimal valueConvert = new BigDecimal(value);
        if (valueConvert.compareTo(new BigDecimal(minValue)) < 0) {
            throw new WsException(WsCode.BAD_REQUEST, fieldName + " phải lớn hơn " + minValue);
        }
        if (valueConvert.compareTo(new BigDecimal(maxValue)) > 0) {
            throw new WsException(WsCode.BAD_REQUEST, fieldName + " phải nhỏ hơn " + maxValue);
        }
    }

    public static void validLongPriceValueMustBeMore(String fieldName, String value, long minValue) {
        BigDecimal valueConvert = new BigDecimal(value);
        if (valueConvert.compareTo(new BigDecimal(minValue)) < 0) {
            throw new WsException(WsCode.BAD_REQUEST, fieldName + " phải lớn hơn " + minValue);
        }
//        if (valueConvert.compareTo(new BigDecimal(maxValue)) > 0) {
//            throw new WsException(WsCode.BAD_REQUEST, fieldName + " phải nhỏ hơn " + maxValue);
//        }
    }

    public static void validEndNotMoreStart(String startFieldName, String startValue, String endFieldName, String endValue, String pattern) {
        Date start = validDateTimeFormat(startFieldName, startValue, pattern);
        Date end = validDateTimeFormat(endFieldName, endValue, pattern);
        if (start.after(end)) {
            throw new WsException(WsCode.END_TIME_MUST_MORE_START_TIME);
        }
    }

    public static void validLongValueMustBeLess(String fieldName, Long value, Long maxValue) {
        if (value > maxValue) {
            throw new WsException(WsCode.BAD_REQUEST, fieldName + MUST_LESS + maxValue);
        }
    }
}
