package com.ws.masterserver.service;

import com.ws.masterserver.dto.admin.order.change_status.ChangeStatusDto;
import com.ws.masterserver.utils.base.rest.CurrentUser;

/**
 * @author myname
 */
public interface AdminOrderService {
    Object changeStatus(CurrentUser currentUser, ChangeStatusDto payload);
}
