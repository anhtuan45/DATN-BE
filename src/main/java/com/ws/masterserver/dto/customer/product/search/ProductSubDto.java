package com.ws.masterserver.dto.customer.product.search;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductSubDto {
    private String id;
    private String name;
    private Long minPrice;
    private Long maxPrice;
    private String materialName;
    private String categoryName;
    private String des;
    private String typeName;
}
