package com.ws.masterserver.service;

import com.ws.masterserver.utils.base.rest.CurrentUser;

/**
 * @author myname
 */
public interface ProductInfoService {
    Object noPage(CurrentUser currentUser);

    Object noCategory(CurrentUser currentUser);

    Object noCategoryUpdate(CurrentUser currentUser, String id);
}
