package com.ws.masterserver.dto.admin.order.search;

import lombok.Builder;
import lombok.Data;

/**
 * @author myname
 */

@Data
@Builder
public class StatusDto {
    private String code;
    private String name;
}
