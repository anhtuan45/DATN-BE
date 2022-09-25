package com.ws.masterserver.dto.customer.user.register;

import lombok.Data;

@Data
public class RegisterDto {
    private String id;

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private String dob;

    private String password;

//    private String gender;
}
