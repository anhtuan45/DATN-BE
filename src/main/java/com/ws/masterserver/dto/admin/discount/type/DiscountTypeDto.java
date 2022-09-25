package com.ws.masterserver.dto.admin.discount.type;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "type")
@Data
@JsonSubTypes({
        @JsonSubTypes.Type(value = PercentTypeDto.class, name = "percent"),
        @JsonSubTypes.Type(value = PriceTypeDto.class, name = "price"),
        @JsonSubTypes.Type(value = ShipTypeDto.class, name = "ship")
})
public abstract class DiscountTypeDto {
}
