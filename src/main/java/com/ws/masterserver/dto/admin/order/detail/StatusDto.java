package com.ws.masterserver.dto.admin.order.detail;

import com.ws.masterserver.utils.constants.enums.RoleEnum;
import com.ws.masterserver.utils.constants.enums.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatusDto {
    private String status;
    private Date createdDate;
    private RoleEnum role;
    private String fullName;
}
