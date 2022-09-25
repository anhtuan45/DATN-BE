package com.ws.masterserver.service;

import com.ws.masterserver.dto.admin.report.customer.CustomerReportReq;
import com.ws.masterserver.dto.admin.report.product.ProductRevenueReq;
import com.ws.masterserver.utils.base.rest.CurrentUser;

public interface CustomerReportService {
    Object get(CurrentUser currentUser, CustomerReportReq req);

    Object export(CurrentUser currentUser, CustomerReportReq payload);
}
