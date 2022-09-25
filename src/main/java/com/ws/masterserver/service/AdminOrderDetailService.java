package com.ws.masterserver.service;

import com.ws.masterserver.dto.admin.order.change_status.ChangeStatusDto;
import com.ws.masterserver.dto.admin.order.search.OrderReq;
import com.ws.masterserver.utils.base.rest.CurrentUser;

/**
 * @author myname
 */
public interface AdminOrderDetailService {
    Object search(CurrentUser currentUser, OrderReq req);
    Object detail(CurrentUser currentUser, String id);
}
