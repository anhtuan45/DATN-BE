package com.ws.masterserver.dto.admin.size.create_update;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SizeDto {
    private String id;
    private String name;
}
