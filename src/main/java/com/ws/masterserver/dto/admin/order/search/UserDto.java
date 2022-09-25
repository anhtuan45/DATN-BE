package com.ws.masterserver.dto.admin.order.search;

import com.ws.masterserver.utils.constants.enums.RoleEnum;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
    private String id;
    private String firstName;
    private String lastName;
    private Boolean gender;
    private String phone;
    private RoleDto role;
    private String combination;
}
