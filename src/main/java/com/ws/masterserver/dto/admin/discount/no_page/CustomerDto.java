package com.ws.masterserver.dto.admin.discount.no_page;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerDto {
    private String id;
    private String name;
    private String email;
}
