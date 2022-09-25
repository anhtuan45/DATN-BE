package com.ws.masterserver.dto.customer.user;

import lombok.Data;

@Data
public class RegisterDto {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Long dob;
//    private Boolean gender;
    private String phone;
    private String avatar;

}