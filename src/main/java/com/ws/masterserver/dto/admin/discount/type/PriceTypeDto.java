package com.ws.masterserver.dto.admin.discount.type;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonTypeName("price")
@AllArgsConstructor
@NoArgsConstructor
public class PriceTypeDto extends DiscountTypeDto {
    /**
     * Giá trị KM
     */
    private String amountValue;
}
