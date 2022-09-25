package com.ws.masterserver.dto.admin.discount.search;

import com.ws.masterserver.utils.base.rest.PageReq;
import lombok.Data;

@Data
public class DiscountRequest {
    private String textSearch;
    private String status;
    private PageReq pageReq;
}
