package com.ws.masterserver.service;

import com.ws.masterserver.dto.customer.user.ProfileDto;
import com.ws.masterserver.utils.base.rest.CurrentUser;

public interface UserInfoService {
    Object updateProfile(CurrentUser currentUser, ProfileDto dto);

    Object personal(CurrentUser currentUser);
}
