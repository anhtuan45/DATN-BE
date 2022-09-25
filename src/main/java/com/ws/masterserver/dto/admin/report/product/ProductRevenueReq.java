package com.ws.masterserver.dto.admin.report.product;

import com.ws.masterserver.utils.base.rest.PageReq;
import lombok.Data;

@Data
public class ProductRevenueReq {
    private String textSearch;
    private String categoryId;
    private PageReq pageReq;
    private String exportType;
}
