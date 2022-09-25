package com.ws.masterserver.utils.common;

import com.ws.masterserver.dto.admin.order.search.StatusDto;
import com.ws.masterserver.utils.constants.enums.StatusEnum;

public class StatusUtils {
    public static StatusEnum getFromStr(String value) {
        try {
            return StatusEnum.valueOf(value);
        } catch (Exception e) {
            return null;
        }
    }

    public static StatusDto getStatusDto(StatusEnum statusEnum) {
        return StatusDto.builder()
                .code(statusEnum.name())
                .name(statusEnum.getName())
                .build();
    }
}
