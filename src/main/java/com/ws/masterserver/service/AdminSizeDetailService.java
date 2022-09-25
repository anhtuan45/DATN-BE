package com.ws.masterserver.service;

import com.ws.masterserver.dto.admin.size.search.SizeReq;
import com.ws.masterserver.utils.base.rest.CurrentUser;

/**
 * @author myname
 */
public interface AdminSizeDetailService {
    Object search(CurrentUser currentUser, SizeReq payload);
}
