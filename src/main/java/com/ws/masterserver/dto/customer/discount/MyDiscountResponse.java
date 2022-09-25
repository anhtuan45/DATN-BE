package com.ws.masterserver.dto.customer.discount;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class MyDiscountResponse {
    private String discountId;
    private Date startDate;
    private Date endDate;
    private String code;
    private List<String> des;
}
