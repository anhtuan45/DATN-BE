package com.ws.masterserver.dto.admin.report.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductRevenueRes {
    private String id;
    private String name;
    private String categoryName;
    private Long optionNumber;
    private Long revenue;
    private String revenueFmt;
    private String categoryId;
}
