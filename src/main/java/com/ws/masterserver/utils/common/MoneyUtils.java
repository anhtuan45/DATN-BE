package com.ws.masterserver.utils.common;

import com.ws.masterserver.utils.base.WsException;
import com.ws.masterserver.utils.constants.WsCode;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 *
 */
@Slf4j
public class MoneyUtils {

    static NumberFormat numberFormat;

    static {
        Locale locale = new Locale("vi", "vn");
        numberFormat = NumberFormat.getCurrencyInstance(locale);
    }

    /**
     * @param value giá trị tiền(long, double)
     * @return template dạng tiền của Vn
     * @ex 10.000, 2.000.000
     */
    public static String format(Long value) {
        if (value == null) {
            return "";
        }
        String result = "";
        try {
            DecimalFormat decimalFormat = (DecimalFormat) DecimalFormat.getInstance(Locale.ENGLISH);
            decimalFormat.applyPattern("###,###,###,###,###,###,###,###,###,###,###,###,###,###,###,###,###,###,###,###,###,###,###,###,###,###,###,###");
            decimalFormat.setRoundingMode(RoundingMode.CEILING);
            result = decimalFormat.format(value).replace(",", ".");
        } catch (Exception e) {
            log.error("getString error: {}", e.getMessage());
        } finally {
            return result;
        }
    }

    public static String format(String value) {
        return format(Long.valueOf(value));
    }

    public static BigDecimal convertToBC(Object value) {
        if (value instanceof String) {
            return BigDecimal.valueOf(Long.valueOf(value.toString()));
        }
        if (value instanceof Long) {
            return BigDecimal.valueOf((long) value);
        }
        throw new WsException(WsCode.INTERNAL_SERVER);
    }

    public static String formatV2(Long value) {
        if (value == null) return numberFormat.format(0L);
        return numberFormat.format(value);
    }

    public static String formatV2(String value) {
        if (StringUtils.isNullOrEmpty(value)) return "";
        return formatV2(Long.valueOf(value));
    }
}
