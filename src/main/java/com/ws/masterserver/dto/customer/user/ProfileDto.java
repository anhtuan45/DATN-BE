package com.ws.masterserver.dto.customer.user;

import lombok.Data;

import java.util.Date;

@Data
public class ProfileDto {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String newPassword;
    private String phone;
//    private Boolean gender;
    private Date dob;
}
