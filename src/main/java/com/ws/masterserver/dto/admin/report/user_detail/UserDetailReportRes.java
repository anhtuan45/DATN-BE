package com.ws.masterserver.dto.admin.report.user_detail;

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
public class UserDetailReportRes {
    private List<UserDetailReportItem> data;
    private Object total;
    private Date start;
    private Date end;
}
