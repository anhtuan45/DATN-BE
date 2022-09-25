package com.ws.masterserver.dto.admin.report.revenue_detail;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RevenueDetailItem {
    private String time;

    private Long orderNumber;

    private Long sale;
    private String saleFmt;

    private Long discount;
    private String discountFmt;

    private Long refund;
    private String refundFmt;

    private Long netSale;
    private String netSaleFmt;

    private Long ship;
    private String shipFmt;

    private Long total;
    private String totalFmt;

    private Long productOrderNumber;
    private Long productRefundNumber;
    private Long productNumber;
}
