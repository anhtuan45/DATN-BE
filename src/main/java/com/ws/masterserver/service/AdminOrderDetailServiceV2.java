package com.ws.masterserver.service;

import com.ws.masterserver.dto.admin.order.search.OrderReq;
import com.ws.masterserver.utils.base.rest.CurrentUser;

/**
 * @author myname
 */
public interface AdminOrderDetailServiceV2 {
    Object search(CurrentUser currentUser, OrderReq payload);
}
