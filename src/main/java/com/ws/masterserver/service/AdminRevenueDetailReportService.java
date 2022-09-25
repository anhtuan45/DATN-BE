package com.ws.masterserver.service;

import com.ws.masterserver.dto.admin.report.overview.ReportOverviewReq;
import com.ws.masterserver.dto.admin.report.revenue_detail.RevenueDetailRes;
import com.ws.masterserver.utils.base.rest.CurrentUser;

/**
 * @author myname
 */
public interface AdminRevenueDetailReportService {
    RevenueDetailRes detail(CurrentUser currentUser, ReportOverviewReq payload);
    Object export(CurrentUser currentUser, ReportOverviewReq payload);
}
