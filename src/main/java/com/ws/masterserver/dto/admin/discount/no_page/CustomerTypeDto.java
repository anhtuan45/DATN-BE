package com.ws.masterserver.dto.admin.discount.no_page;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerTypeDto {
    private String id;
    private String name;
    private Long customerNumber;
}
