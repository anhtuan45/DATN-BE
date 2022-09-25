package com.ws.masterserver.dto.admin.product.search;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ProductSubRes {
    private String id;
    private String name;
    private Long minPrice;
    private Long maxPrice;
    private Long qty;
    private String des;
    private Boolean active;
    private Date createdDate;
    private String materialName;
    private String categoryName;
    private String typeName;

    public ProductSubRes(String id, String name, Long minPrice, Long maxPrice, Long qty, String des, Boolean active, Date createdDate, String materialName, String categoryName, String typeName) {
        this.id = id;
        this.name = name;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.qty = qty;
        this.des = des;
        this.active = active;
        this.createdDate = createdDate;
        this.materialName = materialName;
        this.categoryName = categoryName;
        this.typeName = typeName;
    }
}