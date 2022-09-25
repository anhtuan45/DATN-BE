package com.ws.masterserver.dto.admin.order.change_status;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangeStatusDto {
    private String id;
    private String status;
    private String note;
}
