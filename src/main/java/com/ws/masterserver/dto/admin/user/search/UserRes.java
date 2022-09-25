package com.ws.masterserver.dto.admin.user.search;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRes {
    private String id;
    private String firstName;
    private String lastName;
    private String combinationName;
    private String email;
    private String phone;
    private String role;
    private String roleName;
    private Boolean active;
    private String activeName;
    private String activeClazz;
    private Boolean gender;
    private Date createdDate;
    private String createdDateFmt;
    private String avatar;
    private Date dob;
    private Long dobMil;
    private String dobFmt;
    private Long orderNumber;
    private String customerTypeName;
    private Object customerTypes;
}
