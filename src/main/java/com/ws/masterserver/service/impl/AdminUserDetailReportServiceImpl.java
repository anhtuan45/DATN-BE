package com.ws.masterserver.service.impl;

import com.ws.masterserver.dto.admin.report.overview.ReportOverviewReq;
import com.ws.masterserver.dto.admin.report.user_detail.UserDetailReportItem;
import com.ws.masterserver.dto.admin.report.user_detail.UserDetailReportRes;
import com.ws.masterserver.excel.revenue_detail.RevenueDetailExcel;
import com.ws.masterserver.excel.user_detail.UserDetailExcel;
import com.ws.masterserver.service.AdminUserDetailReportService;
import com.ws.masterserver.utils.base.WsRepository;
import com.ws.masterserver.utils.base.rest.CurrentUser;
import com.ws.masterserver.utils.common.DateUtils;
import com.ws.masterserver.utils.common.JpaUtils;
import com.ws.masterserver.utils.common.StringUtils;
import com.ws.masterserver.utils.constants.enums.ReportTimeTypeEnums;
import com.ws.masterserver.utils.validator.admin.report.AdminReportRevenueDetailValidator;
import com.ws.masterserver.utils.validator.auth.AuthValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminUserDetailReportServiceImpl implements AdminUserDetailReportService {

    private final WsRepository repository;

    @Override
    public UserDetailReportRes report(CurrentUser currentUser, ReportOverviewReq payload) {
        AuthValidator.checkAdmin(currentUser);
        AdminReportRevenueDetailValidator.validDetail(payload);
        ReportTimeTypeEnums typeEnum = ReportTimeTypeEnums.from(payload.getType());
        UserDetailReportRes res = new UserDetailReportRes();
        Date startDate;
        Date endDate = new Date();
        if (null == typeEnum) {
            startDate = DateUtils.toDate(payload.getStart(), DateUtils.F_DDMMYYYY);
            endDate = DateUtils.toDate(payload.getEnd(), DateUtils.F_DDMMYYYY);
        } else {
            if (ReportTimeTypeEnums.BEGIN_OF_THE_YEAR.equals(typeEnum)) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.MONTH, 0);
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                startDate = calendar.getTime();
            } else {
                LocalDate localDate = LocalDate.now();
                localDate = localDate.minusDays(typeEnum.getDayAgo());
                startDate = DateUtils.localDateToDate(localDate);
            }
        }
        res.setStart(startDate);
        res.setEnd(endDate);

        List<Object[]> objects = repository.userRepository.getUserReportByDay(startDate, endDate);
        if (objects.isEmpty()) {
            res.setTotal(0L);
            res.setData(new ArrayList<>(this.getResponseMap(startDate, endDate, payload.getDirection()).values()));
            return res;
        }
        AtomicLong totalAtomicLong = new AtomicLong(0L);
        List<UserDetailReportItem> list = objects.stream().map(obj -> {
            String time = JpaUtils.getString(obj[0]);
            Long total = JpaUtils.getLong(obj[1]);
            totalAtomicLong.addAndGet(total);
            return UserDetailReportItem.builder()
                    .time(time)
                    .total(total)
                    .build();
        }).collect(Collectors.toList());
        res.setTotal(totalAtomicLong.get());
        res.setData(this.getResponseList(list, startDate, endDate, payload.getDirection()));
        return res;
    }

    @Override
    public Object export(CurrentUser currentUser, ReportOverviewReq payload) {
        AuthValidator.checkAdmin(currentUser);
        return UserDetailExcel.export(this.report(currentUser, payload));
    }

    private List<UserDetailReportItem> getResponseList(List<UserDetailReportItem> list, Date startDate, Date endDate, String direction) {
        Map<String, UserDetailReportItem> map = this.getResponseMap(startDate, endDate, direction);
        for (UserDetailReportItem item : list) {
            String keyCompare = item.getTime();
            UserDetailReportItem reportItem = map.get(keyCompare);
            if (null != reportItem) {
                map.replace(keyCompare, reportItem, item);
            }
        }
        return new ArrayList<>(map.values());
    }

    private Map<String, UserDetailReportItem> getResponseMap(Date startDate, Date endDate, String direction) {
        Map<String, UserDetailReportItem> map = new LinkedHashMap<>();
        LocalDate start = DateUtils.dateToLocalDate(startDate);
        LocalDate end = DateUtils.dateToLocalDate(endDate);
        String pattern = DateUtils.F_DDMMYYYY;
        if (StringUtils.isNullOrEmpty(direction) || "asc".equalsIgnoreCase(direction)) {
            end = end.plusDays(1);
            while (start.isBefore(end)) {
                String key = DateUtils.localDateToStr(start, pattern);
                this.addDataToMap(map, key, 0L);
                start = start.plusDays(1);
            }
        } else {
            start = start.minusDays(1);
            while (start.isBefore(end)) {
                String key = DateUtils.localDateToStr(end, pattern);
                this.addDataToMap(map, key, 0L);
                end = end.minusDays(1);
            }
        }
        return map;
    }

    private void addDataToMap(Map<String, UserDetailReportItem> map, String key, long total) {
        map.put(key,
                UserDetailReportItem.builder()
                        .time(key)
                        .total(total)
                        .build());
    }
}
