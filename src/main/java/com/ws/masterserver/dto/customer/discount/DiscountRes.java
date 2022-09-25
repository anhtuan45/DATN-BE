package com.ws.masterserver.dto.customer.discount;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DiscountRes {
    private String discountId;
    private Date startDate;
    private Date endDate;
    private String code;
}
