package com.ws.masterserver.dto.admin.user.search;

import com.ws.masterserver.utils.base.rest.PageReq;
import com.ws.masterserver.utils.constants.enums.RoleEnum;
import lombok.Data;

@Data
public class UserReq {
    private String textSearch;
    private Boolean active;
    private String role;
    private String customerTypeId;
    private PageReq pageReq;
}
