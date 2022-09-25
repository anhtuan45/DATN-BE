package com.ws.masterserver.dto.admin.report.customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerReportRes {
    private String userId;
    private String turnover;
    private Integer purchases;
    private String phone;
    private String fullName;
}
