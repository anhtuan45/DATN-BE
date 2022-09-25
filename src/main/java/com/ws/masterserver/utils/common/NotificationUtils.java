package com.ws.masterserver.utils.common;

import com.ws.masterserver.entity.NotificationEntity;
import com.ws.masterserver.entity.UserEntity;
import com.ws.masterserver.utils.base.WsRepository;
import com.ws.masterserver.utils.constants.enums.NotificationTypeEnum;
import com.ws.masterserver.utils.constants.enums.ObjectTypeEnum;
import lombok.extern.slf4j.Slf4j;

import java.util.Calendar;
import java.util.Date;

@Slf4j
public class NotificationUtils {

    public static final String REGISTER_CUSTOMER_TEMP = "Khách hàng %s vừa đăng kí tài khoản thành công";
    public static final String CUSTOMER_CHECKOUT_TEMP = "Khách hàng %s vừa đặt hàng thành công. Vui lòng xử lý đơn hàng";
    public static final String CUSTOMER_CANCEL_ORDER_TEMP = "Khách hàng %s vừa hủy đơn hàng %s";
    public static final String YOUR_ORDER_HAS_ACCEPTED = "Đơn hàng %s của bạn đã được chấp nhận";
    public static final String YOUR_ORDER_HAS_SHIPPING = "Đơn hàng %s của bạn đang trong quá trình vận chuyển";
    public static final String YOUR_ORDER_HAS_REJECTED = "Đơn hàng %s của bạn đã bị từ chối";


    public static String getCreatedDate(Date createdDate) {
        Calendar calendar = Calendar.getInstance();
        int nowYear = calendar.get(Calendar.YEAR);
        int nowWeek = calendar.get(Calendar.WEEK_OF_YEAR);
        int nowDay = calendar.get(Calendar.DAY_OF_WEEK);
        int nowHour = calendar.get(Calendar.HOUR_OF_DAY);
        int nowMin = calendar.get(Calendar.MINUTE);
        calendar.setTime(createdDate);
        int year = calendar.get(Calendar.YEAR);
        int week = calendar.get(Calendar.WEEK_OF_YEAR);
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);
        if (nowWeek == week) {
            if (nowDay == day) {
                return getTimeOfDay(nowHour, hour, nowMin, min);
            }
            return getDay(day, createdDate);
        }
        if (nowYear == year) {
            return DateUtils.toStr(createdDate, DateUtils.DD_MM);
        }
        return DateUtils.toStr(createdDate, DateUtils.F_DDMMYYYY);
    }


    private static String getTimeOfDay(int nowHour, int hour, int nowMin, int min) {
        if (nowHour - hour < 1) {
            return nowMin - min + " phút trước";
        }
        return nowHour - hour + " giờ trước";
    }

    private static String getDay(int dow, Date createdDate) {
        String day = "";
        String time = DateUtils.toStr(createdDate, DateUtils.HHMM);
        switch (dow) {
            case 1:
                break;
            case 2:
                day = "Thứ hai";
                break;
            case 3:
                day = "Thứ ba";
                break;
            case 4:
                day = "Thứ tư";
                break;
            case 5:
                day = "Thứ năm";
                break;
            case 6:
                day = "Thứ sáu";
                break;
            case 7:
                day = "Thứ bảy";
                break;
            default:
                break;
        }
        return day + ", " + time;
    }
    public static NotificationEntity buildNotification(String format, String objectType, String objectTypeId, String notificationType) {
        NotificationEntity notification = NotificationEntity.builder()
                .id(UidUtils.generateUid())
                .content(format)
                .createdDate(new Date())
                .objectType(objectType)
                .objectTypeId(objectTypeId)
                .type(notificationType)
                .build();
        log.info("buildNotification(): {}", JsonUtils.toJson(notification));
        return notification;
    }
}
