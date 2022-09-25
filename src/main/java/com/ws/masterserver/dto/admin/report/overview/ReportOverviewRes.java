package com.ws.masterserver.dto.admin.report.overview;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportOverviewRes {
    private Object revenues;
    private Object revenueTotal;
    private Object revenueTotalFmt;
    private Object users;
    private Object userTotal;
}
