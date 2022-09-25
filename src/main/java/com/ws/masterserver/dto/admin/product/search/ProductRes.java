package com.ws.masterserver.dto.admin.product.search;

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
public class ProductRes {
    private String id;
    private String name;
    private String des;
    private String minPrice;
    private String maxPrice;
    private Boolean active;
    private String activeName;
    private String activeClazz;
    private Date createdDate;
    private String createdDateFmt;
    private Long qty;
    private String materialName;
    private String categoryName;
    private String typeName;
    private Long soldNumber;
    private List<String> sizes;
    private List<String> colors;
    private String specifications;
    private String specificationsTitle;
    private Long productOptionNumber;
    private Long viewNumber;
    private Long reviewNumber;
}
