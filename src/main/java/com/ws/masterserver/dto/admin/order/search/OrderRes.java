package com.ws.masterserver.dto.admin.order.search;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Builder
@Getter
@Setter
public class OrderRes {
    private String id;
    private String code;
    private String customerName;
    private String phone;
    private Date createdDate;
    private String createdDateFmt;
    private String addressCombination;
    private Long total;
    private String totalFmt;
    private String note;
    private String type;
    private String statusCombination;
    private String statusCode;
    private String statusName;
    private String customerId;
    private List<OptionDto> options;
    private String payment;
    private String paymentName;
    private Boolean payed;
    private Date updatedDate;
    private String updatedDateFmt;
}
