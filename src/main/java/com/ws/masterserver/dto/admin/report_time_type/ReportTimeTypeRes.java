package com.ws.masterserver.dto.admin.report_time_type;

import com.ws.masterserver.utils.constants.enums.ReportTimeTypeEnums;
import lombok.Data;

@Data
public class ReportTimeTypeRes {
    private String code;
    private String value;

    public ReportTimeTypeRes(ReportTimeTypeEnums reportTimeTypeEnums) {
        this.code = reportTimeTypeEnums.name();
        this.value = reportTimeTypeEnums.getName();
    }
}
