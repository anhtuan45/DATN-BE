package com.ws.masterserver.service.impl;

import com.ws.masterserver.dto.admin.report_time_type.ReportTimeTypeRes;
import com.ws.masterserver.service.AdminReportTimeTypeService;
import com.ws.masterserver.utils.base.rest.CurrentUser;
import com.ws.masterserver.utils.constants.enums.ReportTimeTypeEnums;
import com.ws.masterserver.utils.validator.auth.AuthValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminReportTimeTypeServiceImpl implements AdminReportTimeTypeService {
    @Override
    public Object getAll(CurrentUser currentUser) {
        AuthValidator.checkAdmin(currentUser);
        return ReportTimeTypeEnums.getReportTimeTypeEnums().stream().map(ReportTimeTypeRes::new);
    }
}
