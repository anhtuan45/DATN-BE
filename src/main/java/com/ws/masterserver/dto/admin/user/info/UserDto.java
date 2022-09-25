package com.ws.masterserver.dto.admin.user.info;

import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
public class UserDto {
    /**
     * 1: có thể update
     * */

    private String id;
    //1
    private String firstName;
    //1
    private String lastName;
    private String email;
    private String password;
    //1
    private String phone;
    //1
    private String role;
    //1
    private Boolean gender;
    //1
    private Date dob;

    private List<String> customerTypeIds;
}
