package com.ws.masterserver.dto.admin.user.personal;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class PersonalDto {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private Boolean gender;
    private Date dob;
}
