package com.ws.masterserver.utils.common;

import java.text.Normalizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
    public static final String ONLY_CHARACTER_NUMBER = "^[a-zA-Z0-9]+$";
    public static final String SPECIAL_CHARACTERS = "[^A-Za-z0-9_]+";
    public static final String SPECIAL_CHARACTERS_PLUS = "^[a-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂẾưăạảấầẩẫậắằẳẵặẹẻẽềềểếỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựýỳỵỷỹ ()\\s\\\\W|_]+[^@!#$%^&*:\';/?.,<>{}|0123456789]$";
    public static final String BIRTHDAY_REGEX = "\\b^([0-2][0-9]|(3)[0-1])(\\/)(((0)[0-9])|((1)[0-2]))(\\/)\\d{4}\\b";
    public static final String EMAIL_REGEX = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    public static final String INDENTITY_CARD_REGEX = "\\b([0-9]{9}|[0-9]{12})\\b";
    public static final String PHONE_NUMBER_REGEX = "\\b(84|0[3|5|7|8|9])+([0-9]{8})\\b";
    public static final String MONTH_YEAR_REGEX = "\\b^(((0)[0-9])|((1)[0-2]))(\\/)\\d{4}\\b";
    public static final String MONTH_OF_YEAR_REGEX = "[0-9]|1[0-1]";
    public static final String NUMBER_REGEX = "^[0-9]+$";
    public static final String NUMBER_AND_DOT_REGEX = "^[0-9.]+$";
    public static final String PAS_REGEX = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[\\u005C$#^><%&!/`\\[*|\\-{=}+\\]+:?\"()_;.,~@'])+[\\u005C$#^><%&/!`*\\[|\\-{=}+\\]:;?\"()_.,~@'a-zA-Z0-9]{6,32}$";
    public static final String ONLY_NUMBER_REGEX = "[0-9]+";

    private StringUtils() {
    }

    public static boolean isNullOrEmpty(String value) {
        return value == null ||  value.trim().length() == 0;
    }

    /*Chuyen doi ten tieng Viet co dau sang khong co dau*/
    public static String removeAccent(String s) {
        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("")
                .replace("Đ", "D")
                .replace("đ", "")
                .replace("'", " ")
                .replace("-", " ");

    }

    public static String removeAccentV2(String s) {
        return Normalizer.normalize(s, Normalizer.Form.NFKD).replaceAll("\\p{M}", "");
    }

    public static String removeAccentWithoutTrim(String s) {
        return Normalizer.normalize(s, Normalizer.Form.NFKD).replaceAll("\\p{M}", "");
    }

    public static boolean isNumber(String value) {
        if (StringUtils.isNullOrEmpty(value)) {
            return false;
        }
        return value.chars().allMatch(Character::isDigit);
    }

    public static boolean isOnlyCharacterAndNumber(String value) {
        if (StringUtils.isNullOrEmpty(value)) {
            return false;
        }
        value = removeAccent(value).replaceAll(" ", "");
        return isCheck(value, ONLY_CHARACTER_NUMBER);

    }

    public static boolean isCheck(String value, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }

    public static boolean isOnlyNumber(final String value) {
        if (StringUtils.isNullOrEmpty(value)) {
            return false;
        }
        return isCheck(value, ONLY_NUMBER_REGEX);
    }

    public static boolean isOnlyCharacter(String value) {
        if (StringUtils.isNullOrEmpty(value)) {
            return false;
        }
        return value
                .replaceAll("\\s", "")
                .replaceAll("\\p{M}", "")
                .chars().allMatch(Character::isLetter);
    }

    public static boolean isValidEmail(String value) {
        if (StringUtils.isNullOrEmpty(value)) {
            return false;
        }
        return isCheck(value, EMAIL_REGEX);
    }

    public static boolean isValidPhone(String value) {
        if (StringUtils.isNullOrEmpty(value)) {
            return false;
        }
        return value.replaceAll("\\s", "").length() == 10 &&
                isCheck(value, PHONE_NUMBER_REGEX);
    }
}
