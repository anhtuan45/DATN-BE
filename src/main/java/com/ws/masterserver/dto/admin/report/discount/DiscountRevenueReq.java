package com.ws.masterserver.dto.admin.report.discount;

import com.ws.masterserver.utils.base.rest.PageReq;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiscountRevenueReq {
    private String status;
    private String textSearch;
    private PageReq pageReq;
    private String exportType;
}
