package com.ws.masterserver.service;

import com.ws.masterserver.utils.base.rest.CurrentUser;

import java.util.List;

/**
 * @author myname
 */
public interface NotificationService {
    Object get4Admin(CurrentUser currentUser);

    Long readTop3Notification4Admin(CurrentUser currentUser, List<String> dto);

    Object search4Admin(CurrentUser currentUser, Integer pageSize);

    Long readAll4Admin(CurrentUser currentUser);

    void readById4Admin(CurrentUser currentUser, String id);

    Object get4Customer(CurrentUser currentUser, Integer pageSize);

    Object getTop3Noti4Customer(CurrentUser currentUser);

    void readNotification4Customer(CurrentUser current,String id);

    Long readAll4Customer(CurrentUser currentUser);
}
