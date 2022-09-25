package com.ws.masterserver.repository.custom;

import com.ws.masterserver.dto.admin.report.customer.CustomerReportReq;

public interface CustomerRevenueRepository {
    Object get(CustomerReportReq payload);
}
