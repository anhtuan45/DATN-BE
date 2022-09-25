package com.ws.masterserver.service;

import com.ws.masterserver.utils.base.rest.CurrentUser;

public interface OrderDetailService {
    Object getOrderDetail(CurrentUser currentUser,String id);
}
