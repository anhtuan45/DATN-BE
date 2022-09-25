package com.ws.masterserver.service.impl;

import com.ws.masterserver.dto.admin.dashboard.*;
import com.ws.masterserver.service.DashboardService;
import com.ws.masterserver.utils.base.WsRepository;
import com.ws.masterserver.utils.base.rest.CurrentUser;
import com.ws.masterserver.utils.common.JpaUtils;
import com.ws.masterserver.utils.common.MoneyUtils;
import com.ws.masterserver.utils.constants.enums.RoleEnum;
import com.ws.masterserver.utils.validator.auth.AuthValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
@Slf4j
public class DashboardServiceImpl implements DashboardService {
    private final WsRepository repository;

    @Override
    public Object dashboard(CurrentUser currentUser) {
        AuthValidator.checkAdminAndStaff(currentUser);
        DashboardDto dto = new DashboardDto();

        ReportDto report = new ReportDto();
        CompletableFuture<Void> pendingReport = CompletableFuture.runAsync(() -> {
            List<Long> pendingList = repository.orderStatusRepository.countPending();
            report.setPending((long) pendingList.size());
        });
        CompletableFuture<Void> cancelReport = CompletableFuture.runAsync(() -> {
            Long rejectAndCancelNumberToday = repository.orderStatusRepository.countRejectAndCancelToday();
            report.setCancel(rejectAndCancelNumberToday);
        });
        CompletableFuture<Void> userReport = CompletableFuture.runAsync(() -> {
            Long newUserThisWeek = repository.userRepository.countNewUserThisWeek();
            report.setUser(newUserThisWeek);
        });
        CompletableFuture<Void> todayReport = CompletableFuture.runAsync(() -> {
            Long earningToday = repository.orderRepository.getEarningToday();
            report.setToday(MoneyUtils.formatV2(earningToday));
        });
        CompletableFuture<Void> weekReport = CompletableFuture.runAsync(() -> {
            Long earningThisWeek = repository.orderRepository.getEarningThisWeek();
            report.setWeek(MoneyUtils.formatV2(earningThisWeek));
        });

        WeekRevenueDto weeks = new WeekRevenueDto();
        List<DayDto> daysOfThisWeek = new ArrayList<>();
        List<DayDto> daysOfLastWeek = new ArrayList<>();
        CompletableFuture<Void> thisWeek = CompletableFuture.runAsync(() -> {
            List<EarningDayDto> earningDayRes = repository.orderRepository.getEarningThisWeekDashboard().stream().map(obj -> EarningDayDto.builder()
                    .dayOfWeek(JpaUtils.getInt(obj[0]))
                    .total(JpaUtils.getLong(obj[1]))
                    .build()).collect(Collectors.toList());
            daysOfThisWeek.addAll(this.getEarningThisWeekWithDay(earningDayRes));
            weeks.setThisWeek(daysOfThisWeek);
        });
        CompletableFuture<Void> lastWeek = CompletableFuture.runAsync(() -> {
            List<EarningDayDto> earningDayRes = repository.orderRepository.getEarningLastWeekDashboard().stream().map(obj -> EarningDayDto.builder()
                    .dayOfWeek(JpaUtils.getInt(obj[0]))
                    .total(JpaUtils.getLong(obj[1]))
                    .build()).collect(Collectors.toList());
            daysOfLastWeek.addAll(this.getEarningThisWeekWithDay(earningDayRes));
            weeks.setLastWeek(daysOfLastWeek);
        });

        CompletableFuture<Void> categoryFuture = CompletableFuture.runAsync(() -> {
            List<Object[]> objects = repository.orderRepository.getCategoryRevenue();
            dto.setCategory(objects.stream().map(obj -> CategoryRevenueDto.builder()
                    .id(JpaUtils.getString(obj[0]))
                    .name(JpaUtils.getString(obj[1]))
                    .revenue(JpaUtils.getLong(obj[2]))
                    .build()).collect(Collectors.toList()));
        });
        try {
            CompletableFuture.allOf(pendingReport, cancelReport, userReport, todayReport, weekReport,
                    thisWeek, lastWeek,
                    categoryFuture).get();
        } catch (Exception e) {
            log.error("dashboard error: {}", e.getMessage());
        }

        dto.setReport(report);
        dto.setWeek(weeks);
        return dto;
    }

    private List<DayDto> getEarningThisWeekWithDay(List<EarningDayDto> list) {
        Map<Integer, DayDto> map = getDayDtoMap();
        for (EarningDayDto dto : list) {
            DayDto dayDto = map.get(dto.getDayOfWeek());
            if (dayDto != null) {
                map.replace(dto.getDayOfWeek(), dayDto, DayDto.builder()
                        .total(dto.getTotal())
                        .totalFmt(MoneyUtils.formatV2(dto.getTotal()))
                        .build());
            }
        }
        return new ArrayList<>(map.values());
    }

    private Map<Integer, DayDto> getDayDtoMap() {
        Map<Integer, DayDto> map = new LinkedHashMap<>();
        IntStream.rangeClosed(2, 8).forEach(item -> {
            map.put(item, DayDto.builder()
                    .total(0L)
                    .totalFmt(MoneyUtils.formatV2(0L))
                    .build());
        });
        return map;
    }


}
