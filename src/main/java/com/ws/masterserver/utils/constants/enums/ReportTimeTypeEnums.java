package com.ws.masterserver.utils.constants.enums;

import com.ws.masterserver.utils.common.StringUtils;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * @author myname
 */
public enum ReportTimeTypeEnums {
    TODAY("Hôm nay", Calendar.getInstance().get(Calendar.HOUR_OF_DAY), 0),
    YESTERDAY("Hôm qua", 24,1),
    DAYS_AGO7("7 ngày trước", 0, 7),
    DAYS_AGO30("30 ngày trước", 0, 30),
    DAYS_AGO60("60 ngày trước",  0,60),
//    LAST_MONTH("Tháng trước", 0,0),
//    BEGIN_OF_THE_WEEK("Đầu tuần đến nay", 0, 0),
//    BEGIN_OF_THE_MONTH("Đầu tháng đến nay", 0, 0),
//    BEGIN_OF_THE_QUARTER("Đầu quý đến nay", 0, 0),
    BEGIN_OF_THE_YEAR("Đầu năm đến nay", 0,0)
    ;

    private final String name;
    private final Integer dayAgo;
    private final Integer hourAgo;

    ReportTimeTypeEnums(String name, Integer hourAgo, Integer dayAgo){
        this.name = name;
        this.hourAgo = hourAgo;
        this.dayAgo = dayAgo;
    }

    public static ReportTimeTypeEnums from(String type) {
        if (StringUtils.isNullOrEmpty(type)) {
            return null;
        }
        for (ReportTimeTypeEnums item : ReportTimeTypeEnums.values()) {
            if (type.equals(item.name())) {
                return item;
            }
        }
        return null;
    }



    public String getName() {
        return name;
    }

    public Integer getDayAgo() {
        return dayAgo;
    }

    public static List<ReportTimeTypeEnums> getReportTimeTypeEnums() {
        return Arrays.asList(values());
    }
}
