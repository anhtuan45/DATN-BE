package com.ws.masterserver.service;

import com.ws.masterserver.utils.base.rest.CurrentUser;

/**
 * @author myname
 */
public interface AdminDiscountStatusService {
    Object delete(CurrentUser currentUser, String id);
}
