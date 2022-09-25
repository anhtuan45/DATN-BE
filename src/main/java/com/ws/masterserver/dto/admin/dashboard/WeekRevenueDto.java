package com.ws.masterserver.dto.admin.dashboard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WeekRevenueDto {
    private List<DayDto> thisWeek;

    private List<DayDto> lastWeek;
}
