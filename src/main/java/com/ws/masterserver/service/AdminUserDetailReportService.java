package com.ws.masterserver.service;

import com.ws.masterserver.dto.admin.report.overview.ReportOverviewReq;
import com.ws.masterserver.dto.admin.report.user_detail.UserDetailReportRes;
import com.ws.masterserver.utils.base.rest.CurrentUser;

/**
 * @author myname
 */
public interface AdminUserDetailReportService {
    UserDetailReportRes report(CurrentUser currentUser, ReportOverviewReq payload);

    Object export(CurrentUser currentUser, ReportOverviewReq payload);
}
