package com.ws.masterserver.utils.base.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StatusDto {
    private Boolean value;
    private String title;
    private String clazz;
}
