package com.ws.masterserver.excel.product_revenue;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductRevenueDto {
    private Integer index;
    private String id;
    private String name;
    private String categoryName;
    private Long optionNumber;
    private Long revenue;
    private String revenueFmt;

}
