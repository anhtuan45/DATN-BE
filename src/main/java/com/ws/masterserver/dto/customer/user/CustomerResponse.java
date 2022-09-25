package com.ws.masterserver.dto.customer.user;

import com.ws.masterserver.utils.constants.enums.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerResponse {
    private String id;
    private String fullName;
    private RoleEnum role;
    private Date dob;
    private String phone;
    //private Boolean gender
    private String address;
    private String email;
}
