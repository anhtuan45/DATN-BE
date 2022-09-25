package com.ws.masterserver.service;

import com.ws.masterserver.utils.base.rest.CurrentUser;

/**
 * @author myname
 */
public interface AdminCategoryDetailService {
    Object detail(CurrentUser currentUser, String id);
}
