package com.ws.masterserver.utils.base.rest;

import com.ws.masterserver.utils.constants.enums.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CurrentUser {
    private String id;
    private String combinationName;
    private RoleEnum role;
    private String email;
}
