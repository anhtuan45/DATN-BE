package com.ws.masterserver.excel.customer_revenue;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerRevenueDto {
    private Integer index;
    private String userId;
    private String turnover;
    private Integer purchases;
    private String phone;
    private String fullName;
}
