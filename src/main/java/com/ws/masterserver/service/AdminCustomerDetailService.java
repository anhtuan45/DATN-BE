package com.ws.masterserver.service;

import com.ws.masterserver.dto.admin.customer.search.CustomerReq;
import com.ws.masterserver.utils.base.rest.CurrentUser;

public interface AdminCustomerDetailService {
    Object search(CurrentUser currentUser, CustomerReq payload);
}
