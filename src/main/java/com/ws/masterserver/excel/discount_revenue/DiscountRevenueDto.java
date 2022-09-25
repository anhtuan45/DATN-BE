package com.ws.masterserver.excel.discount_revenue;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DiscountRevenueDto {
    private int index;
    private String code;
    private String status;
    private String time;
    private Long revenue;
}
