package com.ws.masterserver.dto.admin.report.revenue_detail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RevenueDetailRes {
    private List<RevenueDetailItem> data;
    private Object total;
    private Date start;
    private Date end;
}
