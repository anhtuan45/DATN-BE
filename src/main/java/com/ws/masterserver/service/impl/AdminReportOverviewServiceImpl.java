package com.ws.masterserver.service.impl;

import com.ws.masterserver.dto.admin.report.overview.ReportOverviewReq;
import com.ws.masterserver.dto.admin.report.overview.ReportOverviewRes;
import com.ws.masterserver.dto.admin.report.overview.RevenueRes;
import com.ws.masterserver.dto.admin.report.overview.UserRes;
import com.ws.masterserver.service.AdminReportOverviewService;
import com.ws.masterserver.utils.base.WsException;
import com.ws.masterserver.utils.base.WsRepository;
import com.ws.masterserver.utils.base.rest.CurrentUser;
import com.ws.masterserver.utils.common.DateUtils;
import com.ws.masterserver.utils.common.JpaUtils;
import com.ws.masterserver.utils.common.MoneyUtils;
import com.ws.masterserver.utils.constants.WsCode;
import com.ws.masterserver.utils.constants.enums.ReportTimeTypeEnums;
import com.ws.masterserver.utils.validator.admin.report.AdminReportOverviewValidator;
import com.ws.masterserver.utils.validator.auth.AuthValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
@Service
@Slf4j
@RequiredArgsConstructor
public class AdminReportOverviewServiceImpl implements AdminReportOverviewService {

    private final WsRepository repository;

    @Override
    public Object overview(CurrentUser currentUser, ReportOverviewReq payload) {
        AuthValidator.checkAdmin(currentUser);
        AdminReportOverviewValidator.validOverview(payload);
        ReportTimeTypeEnums typeEnum = ReportTimeTypeEnums.from(payload.getType());
        ReportOverviewRes res = new ReportOverviewRes();
        LocalDate localDate = LocalDate.now();
        if (typeEnum == null) {
            /**
             * 1. year = 0, month = 0, day == 0 => report By Hour
             * 2.1 year = 0, month = 0, 0 < day <= 60 => report by Day
             * 2.2 year = 0, month <= 2 => report by Day
             * 3. year = 0, 2 < month <= 12 => report by month
             * 4. year > 0 => report by year
             * */
            LocalDate startLocalDate = DateUtils.toLocalDate(payload.getStart(), DateUtils.F_DDMMYYYY);
            LocalDate endLocalDate = DateUtils.toLocalDate(payload.getEnd(), DateUtils.F_DDMMYYYY);
            Period period = Period.between(startLocalDate, endLocalDate);
            int yearDiff = period.getYears();
            int monthDiff = period.getMonths();
            int dayDiff = period.getDays();
            Date startDate = DateUtils.localDateToDate(startLocalDate);
            Date endDate = DateUtils.localDateToDate(endLocalDate);
            /**
             * 1. year = 0, month = 0, day = 0 => report By Hour
             */
            if (yearDiff == 0 && monthDiff == 0 && dayDiff == 0) {
                int hourNow = Calendar.HOUR_OF_DAY;
                this.buildDefaultResponseByHour(res, hourNow);
                CompletableFuture<Void> revenueReportByHourFuture = CompletableFuture.runAsync(() -> {
                    List<Object[]> objects = repository.orderRepository.getRevenueReportByHour(startDate, endDate);
                    this.setRevenuesByHour(res, hourNow, objects);
                });
                CompletableFuture<Void> userReportByHourFuture = CompletableFuture.runAsync(() -> {
                    List<Object[]> objects = repository.userRepository.getUserReportByHour(startDate, endDate);
                    this.setUsersByHour(res, hourNow, objects);
                });
                try {
                    CompletableFuture.allOf(revenueReportByHourFuture, userReportByHourFuture).get();
                } catch (Exception e) {
                    throw new WsException(WsCode.INTERNAL_SERVER);
                }
                return res;
            }
            /**
             * 2.1 year = 0, month = 0, 0 < day <= 60 => report by Day (not occur cuz dayDiff always <= 31)
             * 2.2 year = 0, month <= 2 => report by Day
             */

            if (yearDiff == 0 && monthDiff <= 2) {
                int dayLimit = (int) ChronoUnit.DAYS.between(startLocalDate, endLocalDate) + 1;
                this.buildDefaultResponseByDay(res, dayLimit);
                CompletableFuture<Void> revenueReportByDayFuture = CompletableFuture.runAsync(() -> {
                    List<Object[]> objects = repository.orderRepository.getRevenueReportByDay(startDate, endDate);
                    this.setRevenueByDay(res, dayLimit, objects);
                });
                CompletableFuture<Void> userReportByDayFuture = CompletableFuture.runAsync(() -> {
                    List<Object[]> objects = repository.userRepository.getUserReportByDay(startDate, endDate);
                    this.setUserByDay(res, dayLimit, objects);
                });
                try {
                    CompletableFuture.allOf(revenueReportByDayFuture, userReportByDayFuture).get();
                } catch (Exception e) {
                    throw new WsException(WsCode.INTERNAL_SERVER);
                }
                return res;
            }

            /**
             * 3. year = 0, 2 < month <= 12 => report by month
             */
            if (yearDiff == 0 && monthDiff > 2 && monthDiff <= 12) {
                int monthLimit = monthDiff + 1;
                this.buildDefaultResponseByMonth(res, monthLimit);
                CompletableFuture<Void> revenueReportByMonthFuture = CompletableFuture.runAsync(() -> {
                    List<Object[]> objects = repository.orderRepository.getRevenueReportByMonth(startDate, endDate);
                    this.setRevenueByMonth(res, monthLimit, objects);
                });
                CompletableFuture<Void> userReportByMonthFuture = CompletableFuture.runAsync(() -> {
                    List<Object[]> objects = repository.userRepository.getUserReportByMonth(startDate, endDate);
                    this.setUserByMonth(res, monthLimit, objects);
                });
                try {
                    CompletableFuture.allOf(revenueReportByMonthFuture, userReportByMonthFuture).get();
                } catch (Exception e) {
                    throw new WsException(WsCode.INTERNAL_SERVER);
                }
                return res;
            }

            /**
             * 4. year > 0 => report by year
             * */
            if (yearDiff > 0) {
                this.buildDefaultResponseByYear(res, yearDiff);
                CompletableFuture<Void> revenueReportByYearFuture = CompletableFuture.runAsync(() -> {
                    List<Object[]> objects = repository.orderRepository.getRevenueReportByYear(startDate, endDate);
                    this.setRevenueByYear(res, yearDiff, objects);
                });
                CompletableFuture<Void> userReportByYearFuture = CompletableFuture.runAsync(() -> {
                    List<Object[]> objects = repository.userRepository.getUserReportByYear(startDate, endDate);
                    this.setUserByYear(res, yearDiff, objects);
                });
                try {
                    CompletableFuture.allOf(revenueReportByYearFuture, userReportByYearFuture).get();
                } catch (Exception e) {
                    throw new WsException(WsCode.INTERNAL_SERVER);
                }
                return res;
            }
        } else {
            switch (typeEnum) {
                case TODAY:
                case YESTERDAY:
                    int hourNow = Calendar.HOUR_OF_DAY;
                    Integer dayLimit = typeEnum.getDayAgo();
                    localDate = localDate.minusDays(dayLimit);
                    Date date = DateUtils.localDateToDate(localDate);
                    this.buildDefaultResponseByHour(res, hourNow);
                    CompletableFuture<Void> revenueReportByHourFuture = CompletableFuture.runAsync(() -> {
                        List<Object[]> objects = repository.orderRepository.getRevenueReportByHour(date);
                        this.setRevenuesByHour(res, hourNow, objects);
                    });
                    CompletableFuture<Void> userReportByHourFuture = CompletableFuture.runAsync(() -> {
                        List<Object[]> objects = repository.userRepository.getUserReportByHour(date);
                        this.setUsersByHour(res, hourNow, objects);
                    });
                    try {
                        CompletableFuture.allOf(revenueReportByHourFuture, userReportByHourFuture).get();
                    } catch (Exception e) {
                        throw new WsException(WsCode.INTERNAL_SERVER);
                    }
                    break;
                case DAYS_AGO7:
                case DAYS_AGO30:
                case DAYS_AGO60:
                    Integer day = typeEnum.getDayAgo();
                    localDate = localDate.minusDays(day);
                    Date dateByDay = DateUtils.localDateToDate(localDate);
                    this.buildDefaultResponseByDay(res, day);
                    CompletableFuture<Void> revenueReportByDayFuture = CompletableFuture.runAsync(() -> {
                        List<Object[]> objects = repository.orderRepository.getRevenueReportByDay(dateByDay);
                        this.setRevenueByDay(res, day, objects);
                    });
                    CompletableFuture<Void> userReportByDayFuture = CompletableFuture.runAsync(() -> {
                        List<Object[]> objects = repository.userRepository.getUserReportByDay(dateByDay);
                        this.setUserByDay(res, day, objects);
                    });
                    try {
                        CompletableFuture.allOf(revenueReportByDayFuture, userReportByDayFuture).get();
                    } catch (Exception e) {
                        throw new WsException(WsCode.INTERNAL_SERVER);
                    }
                    break;
                case BEGIN_OF_THE_YEAR:
                    Calendar calendar = Calendar.getInstance();
                    int monthLimit = calendar.get(Calendar.MONTH) + 1;
                    calendar.set(Calendar.MONTH, 0);
                    calendar.set(Calendar.DAY_OF_MONTH, 1);
                    Date dateByMonth = calendar.getTime();
                    this.buildDefaultResponseByMonth(res, monthLimit);
                    CompletableFuture<Void> revenueReportByMonthFuture = CompletableFuture.runAsync(() -> {
                        List<Object[]> objects = repository.orderRepository.getRevenueReportByMonth(dateByMonth);
                        this.setRevenueByMonth(res, monthLimit, objects);
                    });
                    CompletableFuture<Void> userReportByMonthFuture = CompletableFuture.runAsync(() -> {
                        List<Object[]> objects = repository.userRepository.getUserReportByMonth(dateByMonth);
                        this.setUserByMonth(res, monthLimit, objects);
                    });
                    try {
                        CompletableFuture.allOf(revenueReportByMonthFuture, userReportByMonthFuture).get();
                    } catch (Exception e) {
                        throw new WsException(WsCode.INTERNAL_SERVER);
                    }
                    break;
                default:
                    break;
            }
        }
        return res;
    }

    private void setUserByYear(ReportOverviewRes res, int yearLimit, List<Object[]> objects) {
        if (!objects.isEmpty()) {
            AtomicReference<Long> userTotal = new AtomicReference<>(0L);
            List<UserRes> userRes = objects.stream().map(obj -> {
                String timeStr = JpaUtils.getString(obj[0]);
                Long total = JpaUtils.getLong(obj[1]);
                userTotal.updateAndGet(v -> v + total);
                return UserRes.builder().timeStr(timeStr).time(timeStr).total(total).build();
            }).collect(Collectors.toList());
            res.setUserTotal(userTotal);
            res.setUsers(this.getUserListByYear(userRes, yearLimit));
        }
    }

    private List<UserRes> getUserListByYear(List<UserRes> list, int yearLimit) {
        Map<String, UserRes> userResMap = getUserMapByYear(yearLimit);
        list.forEach(i -> {
            UserRes userRes = userResMap.get(i.getTimeStr());
            if (userRes != null) {
                userResMap.replace(i.getTimeStr(), userRes, i);
            }
        });
        return new ArrayList<>(userResMap.values());
    }

    private void setRevenueByYear(ReportOverviewRes res, int yearDiff, List<Object[]> objects) {
        if (!objects.isEmpty()) {
            AtomicLong totalRevenue = new AtomicLong(0L);
            List<RevenueRes> revenueRes = objects.stream().map(obj -> {
                String timeStr = JpaUtils.getString(obj[0]);
                Long total = JpaUtils.getLong(obj[1]);
                totalRevenue.addAndGet(total);
                return RevenueRes.builder().timeStr(timeStr).time(timeStr).total(total).totalFmt(MoneyUtils.formatV2(total)).build();
            }).collect(Collectors.toList());
            res.setRevenueTotal(totalRevenue.get());
            res.setRevenueTotalFmt(MoneyUtils.formatV2(totalRevenue.get()));
            res.setRevenues(this.getRevenueListByYear(revenueRes, yearDiff));
        }
    }

    private List<RevenueRes> getRevenueListByYear(List<RevenueRes> input, int yearDiff) {
        Map<String, RevenueRes> revenueResMap = this.getRevenueMapByYear(yearDiff);
        input.forEach(i -> {
            RevenueRes revenueRes = revenueResMap.get(i.getTimeStr());
            if (revenueRes != null) {
                revenueResMap.replace(revenueRes.getTimeStr(), i);
            }
        });
        return new ArrayList<>(revenueResMap.values());
    }

    private void buildDefaultResponseByYear(ReportOverviewRes res, int yearLimit) {
        res.setRevenues(new ArrayList<>(this.getRevenueMapByYear(yearLimit).values()));
        res.setRevenueTotal(0L);
        res.setRevenueTotalFmt(MoneyUtils.formatV2(0L));
        res.setUsers(new ArrayList<>(this.getUserMapByYear(yearLimit).values()));
        res.setUserTotal(0L);
    }

    private Map<String, UserRes> getUserMapByYear(int yearLimit) {
        Map<String, UserRes> userResMap = new LinkedHashMap<>();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) - yearLimit);
        IntStream.rangeClosed(1, yearLimit + 1).forEach(i -> {
            int year = calendar.get(Calendar.YEAR);
            userResMap.put(year + "", UserRes.builder()
                    .timeStr(year + "")
                    .time(year)
                    .total(0L)
                    .build());
            calendar.set(Calendar.YEAR, year + 1);
        });
        return userResMap;
    }

    private Map<String, RevenueRes> getRevenueMapByYear(int yearLimit) {
        Map<String, RevenueRes> revenueResMap = new LinkedHashMap<>();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) - yearLimit);
        IntStream.rangeClosed(1, yearLimit + 1).forEach(i -> {
            int year = calendar.get(Calendar.YEAR);
            revenueResMap.put(year + "", RevenueRes.builder()
                    .timeStr(year + "")
                    .time(year).total(0L)
                    .totalFmt(MoneyUtils.formatV2(0L))
                    .build());
            calendar.set(Calendar.YEAR, year + 1);
        });
        return revenueResMap;

    }

    private void setUserByMonth(ReportOverviewRes res, int monthLimit, List<Object[]> objects) {
        if (!objects.isEmpty()) {
            AtomicReference<Long> userTotal = new AtomicReference<>(0L);
            List<UserRes> userRes = objects.stream().map(obj -> {
                String timeStr = JpaUtils.getString(obj[0]);
                Long total = JpaUtils.getLong(obj[1]);
                userTotal.updateAndGet(v -> v + total);
                return UserRes.builder().timeStr(timeStr).time(timeStr).total(total).build();
            }).collect(Collectors.toList());
            res.setUserTotal(userTotal);
            res.setUsers(this.getUserListByMonth(userRes, monthLimit));
        }
    }

    private Object getUserListByMonth(List<UserRes> collect, int monthLimit) {
        Map<String, UserRes> userResMap = initUserByMonth(monthLimit);
        collect.forEach(i -> {
            UserRes userRes = userResMap.get(i.getTimeStr());
            if (userRes != null) {
                userResMap.replace(i.getTimeStr(), userRes, i);
            }
        });
        return userResMap.values().stream().collect(Collectors.toList());
    }

    private void setRevenueByMonth(ReportOverviewRes res, int monthNow, List<Object[]> objects) {
        if (!objects.isEmpty()) {
            AtomicLong totalRevenue = new AtomicLong(0L);
            List<RevenueRes> revenueRes = objects.stream().map(obj -> {
                String timeStr = JpaUtils.getString(obj[0]);
                Long total = JpaUtils.getLong(obj[1]);
                totalRevenue.addAndGet(total);
                return RevenueRes.builder().timeStr(timeStr).time(timeStr).total(total).totalFmt(MoneyUtils.formatV2(total)).build();
            }).collect(Collectors.toList());
            res.setRevenueTotal(totalRevenue.get());
            res.setRevenueTotalFmt(MoneyUtils.formatV2(totalRevenue.get()));
            res.setRevenues(this.getRevenueListByMonth(revenueRes, monthNow));
        }
    }

    private Object getRevenueListByMonth(List<RevenueRes> input, int month) {
        Map<String, RevenueRes> revenueResMap = this.getRevenuesMapByMonth(month);
        input.forEach(i -> {
            RevenueRes revenueRes = revenueResMap.get(i.getTimeStr());
            if (revenueRes != null) {
                revenueResMap.replace(revenueRes.getTimeStr(), i);
            }
        });
        return new ArrayList<>(revenueResMap.values());
    }

    private void buildDefaultResponseByMonth(ReportOverviewRes res, int month) {
        res.setRevenues(this.getRevenuesMapByMonth(month).values().stream().collect(Collectors.toList()));
        res.setRevenueTotal(0L);
        res.setRevenueTotalFmt(MoneyUtils.formatV2(0L));
        res.setUsers(this.initUserByMonth(month).values().stream().collect(Collectors.toList()));
        res.setUserTotal(0L);
    }

    private Map<String, UserRes> initUserByMonth(int month) {
        Map<String, UserRes> userResMap = new LinkedHashMap<>();
        int yearNow = Calendar.getInstance().get(Calendar.YEAR);
        IntStream.rangeClosed(1, month).forEach(i -> {
            String monthStr = (i >= 10 ? i + "" : ("0" + i)) + "/" + yearNow;
            userResMap.put(monthStr, UserRes.builder().timeStr(monthStr).time(monthStr).total(0L).build());
        });
        return userResMap;
    }

    private Map<String, RevenueRes> getRevenuesMapByMonth(int month) {
        Map<String, RevenueRes> revenueResMap = new LinkedHashMap<>();
        int yearNow = Calendar.getInstance().get(Calendar.YEAR);
        IntStream.rangeClosed(1, month).forEach(i -> {
            String monthStr = (i >= 10 ? i + "" : ("0" + i)) + "/" + yearNow;
            revenueResMap.put(monthStr, RevenueRes.builder().timeStr(monthStr).time(monthStr).total(0L).totalFmt(MoneyUtils.formatV2(0L)).build());
        });
        return revenueResMap;
    }

    private void setUserByDay(ReportOverviewRes res, int day, List<Object[]> objects) {
        if (!objects.isEmpty()) {
            AtomicReference<Long> userTotal = new AtomicReference<>(0L);
            List<UserRes> userRes = objects.stream().map(obj -> {
                String timeStr = JpaUtils.getString(obj[0]);
                Long total = JpaUtils.getLong(obj[1]);
                userTotal.updateAndGet(v -> v + total);
                return UserRes.builder()
                        .timeStr(timeStr)
                        .time(timeStr)
                        .total(total)
                        .build();
            }).collect(Collectors.toList());
            res.setUserTotal(userTotal);
            res.setUsers(this.getUserListByDay(userRes, day));
        }
    }

    private Object getUserListByDay(List<UserRes> collect, int day) {
        Map<String, UserRes> userResMap = getUserMapByDay(day);
        collect.forEach(i -> {
            UserRes userRes = userResMap.get(i.getTimeStr());
            if (userRes != null) {
                userResMap.replace(i.getTimeStr(), userRes, i);
            }
        });
        return userResMap.values().stream().collect(Collectors.toList());
    }

    private void setRevenueByDay(ReportOverviewRes res, int day, List<Object[]> objects) {
        if (!objects.isEmpty()) {
            AtomicLong totalRevenue = new AtomicLong(0L);
            List<RevenueRes> revenueRes = objects.stream().map(obj -> {
                String timeStr = JpaUtils.getString(obj[0]);
                Long total = JpaUtils.getLong(obj[1]);
                totalRevenue.addAndGet(total);
                return RevenueRes.builder()
                        .timeStr(timeStr)
                        .time(timeStr)
                        .total(total)
                        .totalFmt(MoneyUtils.formatV2(total))
                        .build();
            }).collect(Collectors.toList());
            res.setRevenueTotal(totalRevenue.get());
            res.setRevenueTotalFmt(MoneyUtils.formatV2(totalRevenue.get()));
            res.setRevenues(this.getRevenueListByDay(revenueRes, day));
        }
    }

    private Object getRevenueListByDay(List<RevenueRes> input, int day) {
        Map<String, RevenueRes> revenueResMap = getRevenueMapByDay(day);
        input.forEach(i -> {
            RevenueRes revenueRes = revenueResMap.get(i.getTimeStr());
            if (revenueRes != null) {
                revenueResMap.replace(revenueRes.getTimeStr(), i);
            }
        });
        return new ArrayList<>(revenueResMap.values());
    }

    private void buildDefaultResponseByDay(ReportOverviewRes res, int day) {
        res.setRevenues(new ArrayList<>(this.getRevenueMapByDay(day).values()));
        res.setRevenueTotal(0L);
        res.setRevenueTotalFmt(MoneyUtils.formatV2(0L));
        res.setUsers(new ArrayList<>(this.getUserMapByDay(day).values()));
        res.setUserTotal(0L);
    }

    private Map<String, UserRes> getUserMapByDay(int day) {
        LocalDate localDate = LocalDate.now();
        localDate = localDate.minusDays(day - 1);
        Map<String, UserRes> userResMap = new LinkedHashMap<>();
        for (int i = 1; i <= day; i++) {
            String key = DateUtils.localDateToStr(localDate, DateUtils.F_DDMMYYYY);
            UserRes userRes = UserRes.builder().time(localDate).timeStr(key).total(0L).build();
            userResMap.put(key, userRes);
            localDate = localDate.plusDays(1);
        }
        return userResMap;
    }

    private Map<String, RevenueRes> getRevenueMapByDay(int day) {
        LocalDate localDate = LocalDate.now();
        localDate = localDate.minusDays(day - 1);
        Map<String, RevenueRes> revenueResMap = new LinkedHashMap<>();
        for (int i = 1; i <= day; i++) {
            String key = DateUtils.localDateToStr(localDate, DateUtils.F_DDMMYYYY);
            RevenueRes revenueRes = RevenueRes.builder().time(key).timeStr(key).total(0L).totalFmt(MoneyUtils.formatV2(0L)).build();
            revenueResMap.put(key, revenueRes);
            localDate = localDate.plusDays(1);
        }
        return revenueResMap;
    }

    private void setUsersByHour(ReportOverviewRes res, int hourNow, List<Object[]> objects) {
        if (!objects.isEmpty()) {
            AtomicLong userTotal = new AtomicLong(0L);
            List<UserRes> userRes = objects.stream().map(obj -> {
                String hourStr = JpaUtils.getString(obj[0]);
                Long total = JpaUtils.getLong(obj[1]);
                userTotal.addAndGet(total);
                return UserRes.builder().timeStr(hourStr).time(Integer.parseInt(hourStr)).total(total).build();
            }).collect(Collectors.toList());
            res.setUserTotal(userTotal);
            res.setUsers(this.getUserListByHour(userRes, hourNow));
        }
    }

    /**
     * Khởi tạo response trong trường hợp trả về theo giờ
     */
    private void buildDefaultResponseByHour(ReportOverviewRes res, int hour) {
        res.setRevenues(new ArrayList<>(this.getRevenueMapByHour(hour).values()));
        res.setRevenueTotal(0L);
        res.setRevenueTotalFmt(MoneyUtils.formatV2(0L));
        res.setUsers(new ArrayList<>(this.getUserMapByHour(hour).values()));
        res.setUserTotal(0L);
    }

    /**
     * Với trường hợp có dữ liệu, xử lý dựa trên response đã khởi tạo
     */
    private void setRevenuesByHour(ReportOverviewRes res, int hour, List<Object[]> objects) {
        if (!objects.isEmpty()) {
            AtomicLong totalRevenue = new AtomicLong(0L);
            List<RevenueRes> revenueRes = objects.stream().map(obj -> {
                String hourStr = JpaUtils.getString(obj[0]);
                Long total = JpaUtils.getLong(obj[1]);
                totalRevenue.addAndGet(total);
                return RevenueRes.builder()
                        .timeStr(hourStr)
                        .time(Integer.parseInt(hourStr))
                        .total(total)
                        .totalFmt(MoneyUtils.formatV2(total))
                        .build();
            }).collect(Collectors.toList());
            res.setRevenueTotal(totalRevenue.get());
            res.setRevenueTotalFmt(MoneyUtils.formatV2(totalRevenue.get()));
            res.setRevenues(this.getRevenueListByHour(revenueRes, hour));
        }
    }

    private List<UserRes> getUserListByHour(List<UserRes> collect, int hourNow) {
        Map<String, UserRes> userTodayResMap = getUserMapByHour(hourNow);
        collect.forEach(i -> {
            UserRes userRes = userTodayResMap.get(i.getTimeStr());
            if (userRes != null) {
                userTodayResMap.replace(i.getTimeStr(), userRes, i);
            }
        });
        return new ArrayList<>(userTodayResMap.values());
    }

    private Map<String, UserRes> getUserMapByHour(int hourNow) {
        Map<String, UserRes> userHourResHashMap = new LinkedHashMap<>();
        IntStream.rangeClosed(0, hourNow - 1).forEach(i -> {
            String hourStr = i >= 10 ? i + "" : ("0" + i);
            userHourResHashMap.put(hourStr, UserRes.builder().timeStr(hourStr).time(i).total(0L).build());
        });
        return userHourResHashMap;
    }

    private List<RevenueRes> getRevenueListByHour(List<RevenueRes> input, int hourNow) {
        Map<String, RevenueRes> revenueResMap = getRevenueMapByHour(hourNow);
        input.forEach(i -> {
            RevenueRes revenueRes = revenueResMap.get(i.getTimeStr());
            if (revenueRes != null) {
                revenueResMap.replace(revenueRes.getTimeStr(), i);
            }
        });
        return new ArrayList<>(revenueResMap.values());
    }

    @NotNull
    private Map<String, RevenueRes> getRevenueMapByHour(int hourNow) {
        Map<String, RevenueRes> revenueHourResMap = new LinkedHashMap<>();
        IntStream.rangeClosed(0, hourNow - 1).forEach(i -> {
            String hourStr = i >= 10 ? i + "" : ("0" + i);
            revenueHourResMap.put(hourStr, RevenueRes.builder().time(i).timeStr(hourStr).total(0L).totalFmt(MoneyUtils.formatV2(0L)).build());
        });
        return revenueHourResMap;
    }
}
