package com.ws.masterserver.dto.admin.report.user_detail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailReportItem {
    private String time;
    private Long total;
}
