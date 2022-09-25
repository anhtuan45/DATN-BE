package com.ws.masterserver.dto.admin.report.overview;

import lombok.Data;

@Data
public class ReportOverviewReq {
    private String type;
    private String start;
    private String end;
    private String direction;
}
