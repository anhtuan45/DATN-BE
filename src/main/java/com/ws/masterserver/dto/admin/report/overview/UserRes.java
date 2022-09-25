package com.ws.masterserver.dto.admin.report.overview;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRes {
    private String timeStr;
    private Object time;
    private Object total;

}
