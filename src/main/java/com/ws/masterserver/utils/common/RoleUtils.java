package com.ws.masterserver.utils.common;

import com.ws.masterserver.dto.admin.order.search.RoleDto;
import com.ws.masterserver.utils.constants.enums.RoleEnum;

public class RoleUtils {
    public static RoleDto getRoleDto(String value) {
        try {
            RoleEnum role = RoleEnum.valueOf(value);
            return RoleDto.builder()
                    .code(role.name())
                    .name(role.getName())
                    .build();
        } catch (Exception e) {
            return new RoleDto();
        }
    }

    public static String getRoleNameFromCode(String code) {
        try {
            RoleEnum role = RoleEnum.valueOf(code);
            return role.getName();
        } catch (Exception e) {
            return "";
        }
    }
}
