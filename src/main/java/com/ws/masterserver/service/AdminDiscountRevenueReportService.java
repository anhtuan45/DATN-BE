package com.ws.masterserver.service;

import com.ws.masterserver.dto.admin.report.discount.DiscountRevenueReq;
import com.ws.masterserver.utils.base.rest.CurrentUser;

public interface AdminDiscountRevenueReportService {
    Object get(CurrentUser currentUser, DiscountRevenueReq payload);

    Object export(CurrentUser currentUser, DiscountRevenueReq payload);
}
