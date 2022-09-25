package com.ws.masterserver.dto.admin.report.overview;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RevenueRes {
    private String timeStr;
    private Object time;
    private Object total;
    private Object totalFmt;
}
