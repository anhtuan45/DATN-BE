package com.ws.masterserver.dto.admin.discount.no_page;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class DiscountTypeDto {
    private String id;
    private String name;
}
