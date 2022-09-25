package com.ws.masterserver.dto.admin.report.discount;

import com.ws.masterserver.utils.base.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DiscountRevenueRes {
    private String id;
    private String code;
    private StatusDto status;
    private Date startDate;
    private String startDateFmt;
    private Date endDate;
    private String endDateFmt;
    private Long revenue;
    private String revenueFmt;
}
