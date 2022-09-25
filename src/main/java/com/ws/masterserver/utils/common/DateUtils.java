package com.ws.masterserver.utils.common;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Slf4j
public class DateUtils {


    private DateUtils() {
    }


    public static final String F_DDMM = "dd/MM";
    public static final String F_DDMMYYYYHHMMSS = "dd/MM/yyyy HH:mm:ss";
    public static final String F_DDMMYYYY = "dd/MM/yyyy";
    public static final String F_MMYYYY = "MM/yyyy";
    public static final String DATE_TIME_FORMAT_VI = "dd-MM-yyyy HH:mm:ss";
    public static final String DATE_TIME_FORMAT_VI_OUTPUT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_TIME_FORMAT_GENERAL = "EEE MMM dd HH:mm:ss zzz yyyy";
    public static final String YYYY = "yyyy";
    public static final String YYMMDD = "yyMMdd";
    public static final String YYYYMMDD = "yyyyMMdd";
    public static final String YYYY_MM = "yyyy-MM";
    public static final String MM_YYYY = "MM/yyyy";
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYY_MM_DD_HH_MM = "yyyy/MM/dd HH:mm";
    public static final String HH_MM_SS = "HH:mm:ss";
    public static final String HHMMSS = "HHmmss";
    public static final String F_DDMMYYYYHHMM = "dd/MM/yyyy HH:mm";
    public static final String F_VI = "HH:mm:ss dd/MM/yyyy";
    public static final String DD_MM = "dd/MM";
    public static final String HHMM = "HH:mm";

    public static final List<String> patterns;

    static {
        patterns = new ArrayList<>();
        patterns.add(DATE_TIME_FORMAT_VI);
        patterns.add(DATE_TIME_FORMAT_VI_OUTPUT);
        patterns.add(F_DDMMYYYYHHMMSS);
        patterns.add(DATE_TIME_FORMAT_GENERAL);
        patterns.add(F_DDMMYYYY);
        patterns.add(MM_YYYY);
    }

    public static Date addDays(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, days);
        return calendar.getTime();
    }

    public static Date addHours(Date date, int hours) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, hours);
        return calendar.getTime();
    }

    public static Date addMinute(Date date, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, minute);
        return calendar.getTime();
    }

    public static Date toDate(String date, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.parse(date);
        } catch (Exception e) {
            log.error("toDate error: {}", e.getMessage());
            return new Date();
        }
    }

    public static Date toDate(String date, String format, Date defaultVal) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.parse(date);
        } catch (Exception e) {
            return defaultVal;
        }
    }

    public static Date formatDate(Date defaultVal, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            String strDate = sdf.format(defaultVal);
            return sdf.parse(strDate);
        } catch (Exception e) {
            return defaultVal;
        }
    }

    public static String format(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat(F_DDMMYYYY);
        return formatter.format(date);
    }

    public static Date toDateElseNull(String date, String format) {
        return toDate(date, format, null);
    }

    public static String toStr(Date date, String format) {
        return date != null ? DateFormatUtils.format(date, format) : "";
    }

    public static boolean isValid(String date, String format) {
        try {
            LocalDate.parse(date, DateTimeFormatter.ofPattern(format));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static Date fromMothYear(String date, String format) {
        final YearMonth yearMonth = YearMonth.parse(date, DateTimeFormatter.ofPattern(format));
        return Date.from(yearMonth.atDay(1)
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant());
    }

    public static boolean isValidMonthYear(String date, String format) {
        try {
            YearMonth.parse(date, DateTimeFormatter.ofPattern(format));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static Date toDate(String dateStr, List<String> patterns) {

        Date date = null;
        for (String pattern : patterns) {
            try {
                date = new SimpleDateFormat(pattern).parse(dateStr);
                break;
            } catch (ParseException e) {
                log.info(e.getMessage());
            }
        }
        return date;
    }

    public static Date getNowDate() {
        return new Date();
    }

    public static String getDate() {
        return dateTimeNow(YYYY_MM_DD);
    }

    public static String getDateYmd() {
        return dateTimeNow(YYMMDD);
    }

    public static String getTime() {
        return dateTimeNow(YYYY_MM_DD_HH_MM_SS);
    }

    public static String getDateYmdHm() {
        return dateTimeNow(YYYY_MM_DD_HH_MM);
    }

    public static String dateTimeNow() {
        return dateTimeNow(YYYYMMDDHHMMSS);
    }

    public static String datePath() {
        return dateTimeNow("yyyy/MM/dd");
    }

    public static String dateTime() {
        return dateTimeNow(YYYYMMDD);
    }

    public static String dateTimeNow(final String format) {
        return parseDateToStr(format, new Date());
    }

    public static String parseDateToStr(final String format, final Date date) {
        return new SimpleDateFormat(format).format(date);
    }

    public static Long dateToLong(final String dateString, final String dateFormat) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        Date date = sdf.parse(dateString);
        return date.getTime();
    }

    public static String longToDate(final long lo, final String dateFormat) {
        Date date = new Date(lo);
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        return sdf.format(date);
    }

    public static boolean checkDate(final String dateStr, final String format) {
        if (StringUtils.isNullOrEmpty(dateStr)) {
            return false;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        sdf.setLenient(false);

        try {
            Date date = sdf.parse(dateStr);
            if (!sdf.format(date).equals(dateStr)) {
                return false;
            }
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    public static boolean checkDate(String dateStr, List<String> patterns) {

        Date date = null;
        for (String pattern : patterns) {
            try {
                date = new SimpleDateFormat(pattern).parse(dateStr);
                if (date != null) {
                    return true;
                }
            } catch (ParseException e) {
                log.info(e.getMessage());
            }
        }
        return false;
    }

    public static Date localDateTimeToDate(LocalDateTime ldtValue) {
        return Timestamp.valueOf(ldtValue);
    }

    public static LocalDate dateToLocalDate(Date dateValue) {
        return new java.sql.Date(dateValue.getTime()).toLocalDate();
    }

    public static LocalDateTime dateToLocalDateTime(Date dateValue) {
        return new Timestamp(dateValue.getTime()).toLocalDateTime();
    }

    public static String localDateToStr(LocalDate localDate, String pattern) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        return localDate.format(dateTimeFormatter);
    }

    public static Date localDateToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDate toLocalDate(String str, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDate.parse(str, formatter);
    }
}
