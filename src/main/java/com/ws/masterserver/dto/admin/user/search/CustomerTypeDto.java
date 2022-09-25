package com.ws.masterserver.dto.admin.user.search;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerTypeDto {
    private String id;
    private String name;
}
