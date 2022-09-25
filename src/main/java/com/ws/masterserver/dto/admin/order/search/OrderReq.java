package com.ws.masterserver.dto.admin.order.search;

import com.ws.masterserver.utils.base.rest.PageReq;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderReq {
    private String id;
    private String status;
    private String customerId;
    private String provinceId;
    private String districtId;
    private String wardCode;
    private String time;
    private String textSearch;
    private PageReq pageReq;
}
