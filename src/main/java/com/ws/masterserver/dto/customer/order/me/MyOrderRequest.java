package com.ws.masterserver.dto.customer.order.me;

import com.ws.masterserver.utils.constants.enums.StatusEnum;
import com.ws.masterserver.utils.base.rest.PageReq;
import lombok.Data;

@Data
public class MyOrderRequest {
    /** trạng thái đơn hàng */
    private StatusEnum status;

    /** từ ngày */
    private Long startDate;

    /** đến ngày */
    private Long endDate;

    /** trong vòng bnh ngày */
    private Long lastDay;

    /** trong vòng bnh tuần */
    private Long lastWeek;

    /** trong vòng bnh tháng */
    private Long lastMonth;

    private PageReq pageReq;
}
