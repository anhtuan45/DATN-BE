package com.ws.masterserver.utils.base.enum_dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author myname
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SizeDto {
    private String code;
    private String name;
}
