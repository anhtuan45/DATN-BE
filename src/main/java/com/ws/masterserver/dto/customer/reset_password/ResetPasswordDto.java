package com.ws.masterserver.dto.customer.reset_password;

import lombok.Data;

@Data
public class ResetPasswordDto {
    private String token;
    private String password;
}
