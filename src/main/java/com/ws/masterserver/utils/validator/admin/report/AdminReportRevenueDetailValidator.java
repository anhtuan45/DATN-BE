package com.ws.masterserver.utils.validator.admin.report;

import com.ws.masterserver.dto.admin.report.overview.ReportOverviewReq;
import com.ws.masterserver.utils.base.WsException;
import com.ws.masterserver.utils.common.DateUtils;
import com.ws.masterserver.utils.common.StringUtils;
import com.ws.masterserver.utils.common.ValidatorUtils;
import com.ws.masterserver.utils.constants.WsCode;
import com.ws.masterserver.utils.constants.enums.ReportTimeTypeEnums;

/**
 * @author myname
 */
public class AdminReportRevenueDetailValidator {
    private static final String START = "Ngày bắt đầu";
    private static final String END = "Ngày kết thúc";

    public static void validDetail(ReportOverviewReq payload) {
        if (StringUtils.isNullOrEmpty(payload.getType())) {
            ValidatorUtils.validNullOrEmpty(START, payload.getStart());
            ValidatorUtils.validNullOrEmpty(END, payload.getEnd());
            ValidatorUtils.validEndNotMoreStart(START, payload.getStart(), END, payload.getEnd(), DateUtils.F_DDMMYYYY);
        } else {
            ReportTimeTypeEnums typeEnum = ReportTimeTypeEnums.from(payload.getType());
            if (typeEnum == null) {
                throw new WsException(WsCode.ERROR_NOT_FOUND);
            }
        }
    }
}
