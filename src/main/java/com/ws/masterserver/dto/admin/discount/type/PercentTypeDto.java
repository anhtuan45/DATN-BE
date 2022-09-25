package com.ws.masterserver.dto.admin.discount.type;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Builder;
import lombok.Data;

@Data
@JsonTypeName("percent")
@Builder
public class PercentTypeDto extends DiscountTypeDto {

    /**
     * Giá trị km
     */
    private String percentageValue;

    /**
     * Giá trị giảm tối đa
     */
    private String valueLimitAmount;
}
