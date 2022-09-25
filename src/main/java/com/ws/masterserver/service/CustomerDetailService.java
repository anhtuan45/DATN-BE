package com.ws.masterserver.service;

import com.ws.masterserver.dto.customer.reset_password.ResetPasswordDto;
import com.ws.masterserver.dto.customer.user.register.RegisterDto;

public interface CustomerDetailService {
    Object register(RegisterDto payload);
    Object sendMail4ForgotPass(String email);

    Object resetPassword(ResetPasswordDto payload);
}
