package com.ws.masterserver.dto.customer.product.search;

import com.ws.masterserver.utils.base.rest.PageReq;
import com.ws.masterserver.utils.constants.enums.ColorEnum;
import com.ws.masterserver.utils.constants.enums.SizeEnum;
import lombok.Data;

import java.util.List;

@Data
public class ProductReq {
    private String textSearch;
    private String minPrice;
    private String maxPrice;
    private String category;
    private List<String> sizeIds;
    private List<String> colorIds;
    private PageReq pageReq;
}