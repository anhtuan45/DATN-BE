package com.ws.masterserver.dto.admin.dashboard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EarningDayDto {
    private Integer dayOfWeek;
    private Long total;

}
