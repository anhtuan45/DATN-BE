package com.ws.masterserver.utils.base.enum_dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TypeDto {
    private String code;
    private String name;
}
