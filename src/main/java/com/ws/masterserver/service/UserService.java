package com.ws.masterserver.service;


import com.ws.masterserver.dto.customer.user.RegisterDto;
import com.ws.masterserver.utils.base.rest.CurrentUser;

public interface UserService {

    Object registerCustomer(RegisterDto body);

    Object getCurrentUserProfile();
}
