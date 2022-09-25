package com.ws.masterserver.dto.admin.report.customer;

import com.ws.masterserver.utils.base.rest.PageReq;
import lombok.Data;

@Data
public class CustomerReportReq {
    private String textSearch;
    private PageReq pageReq;
    private String exportType;
}
