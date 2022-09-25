package com.ws.masterserver.service;

import com.ws.masterserver.dto.admin.report.overview.ReportOverviewReq;
import com.ws.masterserver.utils.base.rest.CurrentUser;

/**
 * @author myname
 */
public interface AdminReportOverviewService {
    Object overview(CurrentUser currentUser, ReportOverviewReq payload);
}
