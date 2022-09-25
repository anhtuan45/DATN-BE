package com.ws.masterserver.service;

import com.ws.masterserver.dto.admin.user.info.UserDto;
import com.ws.masterserver.utils.base.rest.CurrentUser;

public interface AdminUserInfoService {
    Object create(CurrentUser currentUser, UserDto dto);

    /**
     * chỉ update 1 số trường nhất định(xem class dto)
     */
    Object update(CurrentUser currentUser, UserDto dto);

    Object changeStatus(CurrentUser currentUser, String id);

    Object delete(CurrentUser currentUser, String id);
}
