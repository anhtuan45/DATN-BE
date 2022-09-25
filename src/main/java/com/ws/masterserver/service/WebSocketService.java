package com.ws.masterserver.service;

import com.ws.masterserver.utils.constants.enums.NotificationTypeEnum;
import com.ws.masterserver.utils.constants.enums.ObjectTypeEnum;

/**
 * @author myname
 */
public interface WebSocketService {
    void changeUnreadNumberNotification4Admin();

    void testNotification4Admin(NotificationTypeEnum type, String content, ObjectTypeEnum objectType, String objectTypeId);

    void testDashboard4Admin(int type);

    void testV2(String message);
}
