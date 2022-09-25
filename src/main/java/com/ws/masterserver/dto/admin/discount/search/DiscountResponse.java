package com.ws.masterserver.dto.admin.discount.search;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class DiscountResponse {
    private String id;
    private String code;
    private List<String> des;
    private String status;
    private String statusName;
    private Long usageNumber;
    private Long usageLimit;
    private Date startDate;
    private String startDateFmt;
    private Date endDate;
    private String endDateFmt;
    private Date createdDate;
    private String createdDateFmt;
    private String statusClazz;
    private Boolean canEdit;
}
