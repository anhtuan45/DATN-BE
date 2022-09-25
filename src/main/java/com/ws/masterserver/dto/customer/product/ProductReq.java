package com.ws.masterserver.dto.customer.product;

import com.ws.masterserver.utils.base.rest.PageReq;
import lombok.Data;

@Data
public class ProductReq {
    private String id;
    private Boolean active;
    private String textSearch;
    private String minPrice;
    private String maxPrice;
    private PageReq pageReq;
    private String typeId;
    private String categoryId;
}
