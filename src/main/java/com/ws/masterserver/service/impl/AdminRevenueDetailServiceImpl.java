package com.ws.masterserver.service.impl;

import com.ws.masterserver.dto.admin.report.overview.ReportOverviewReq;
import com.ws.masterserver.dto.admin.report.revenue_detail.RevenueDetailItem;
import com.ws.masterserver.dto.admin.report.revenue_detail.RevenueDetailRes;
import com.ws.masterserver.excel.revenue_detail.RevenueDetailExcel;
import com.ws.masterserver.service.AdminRevenueDetailReportService;
import com.ws.masterserver.utils.base.WsRepository;
import com.ws.masterserver.utils.base.rest.CurrentUser;
import com.ws.masterserver.utils.common.DateUtils;
import com.ws.masterserver.utils.common.JpaUtils;
import com.ws.masterserver.utils.common.MoneyUtils;
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
@Slf4j
@RequiredArgsConstructor
public class AdminRevenueDetailServiceImpl implements AdminRevenueDetailReportService {
    private final WsRepository repository;

    @Override
    public RevenueDetailRes detail(CurrentUser currentUser, ReportOverviewReq payload) {
        AuthValidator.checkAdmin(currentUser);
        AdminReportRevenueDetailValidator.validDetail(payload);
        ReportTimeTypeEnums typeEnum = ReportTimeTypeEnums.from(payload.getType());
        RevenueDetailRes res = new RevenueDetailRes();
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
        List<Object[]> objects = repository.orderRepository.getRevenueDetailReport(startDate, endDate);
        if (objects.isEmpty()) {
            res.setTotal(MoneyUtils.formatV2(0L));
            res.setData(new ArrayList<>(this.getRevenueDetailReportResponseMap(startDate, endDate, payload.getDirection()).values()));
            return res;
        }
        AtomicLong totalAtomicLong = new AtomicLong(0L);
        List<RevenueDetailItem> revenueDetailRes = objects.stream().map(obj -> {
            //doanh thu
            Long sale = JpaUtils.getLong(obj[2]);
            //giamgia
            Long discount = JpaUtils.getLong(obj[3]);
            //tra lai hang
            Long refund = JpaUtils.getLong(obj[4]);
            //doanh thu thuc
            Long netSale = JpaUtils.getLong(obj[5]);
            //van chuyen
            Long ship = JpaUtils.getLong(obj[6]);
            //tong doanh thu
            Long total = JpaUtils.getLong(obj[7]);
            totalAtomicLong.addAndGet(total);
            return RevenueDetailItem.builder()
                    .time(JpaUtils.getString(obj[0]))
                    .orderNumber(JpaUtils.getLong(obj[1]))
                    .sale(sale)
                    .saleFmt(MoneyUtils.formatV2(sale))
                    .discount(discount)
                    .discountFmt(MoneyUtils.formatV2(discount))
                    .refund(refund)
                    .refundFmt(MoneyUtils.formatV2(refund))
                    .netSale(netSale)
                    .netSaleFmt(MoneyUtils.formatV2(netSale))
                    .ship(ship)
                    .shipFmt(MoneyUtils.formatV2(ship))
                    .total(total)
                    .totalFmt(MoneyUtils.formatV2(total))
                    .productOrderNumber(JpaUtils.getLong(obj[8]))
                    .productRefundNumber(JpaUtils.getLong(obj[9]))
                    .productNumber(JpaUtils.getLong(obj[10]))
                    .build();
        }).collect(Collectors.toList());
        res.setTotal(MoneyUtils.formatV2(totalAtomicLong.get()));
        res.setData(this.getRevenueDetailReportResponseList(revenueDetailRes, startDate, endDate, payload.getDirection()));
        return res;
    }

    @Override
    public Object export(CurrentUser currentUser, ReportOverviewReq payload) {
        return RevenueDetailExcel.export(this.detail(currentUser, payload));
    }

    private List<RevenueDetailItem> getRevenueDetailReportResponseList(List<RevenueDetailItem> revenueDetailRes, Date startDate, Date endDate, String direction) {
        Map<String, RevenueDetailItem> stringRevenueDetailResMap = this.getRevenueDetailReportResponseMap(startDate, endDate, direction);
        for (RevenueDetailItem detailRes : revenueDetailRes) {
            String keyCompare = detailRes.getTime();
            RevenueDetailItem itemOfMap = stringRevenueDetailResMap.get(keyCompare);
            if (null != itemOfMap) {
                stringRevenueDetailResMap.replace(keyCompare, itemOfMap, detailRes);
            }
        }
        return new ArrayList<>(stringRevenueDetailResMap.values());
    }

    private Map<String, RevenueDetailItem> getRevenueDetailReportResponseMap(Date startDate, Date endDate, String direction) {
        Map<String, RevenueDetailItem> revenueDetailRes = new LinkedHashMap<>();
        LocalDate start = DateUtils.dateToLocalDate(startDate);
        LocalDate end = DateUtils.dateToLocalDate(endDate);
        String pattern = DateUtils.F_DDMMYYYY;
        String _0VND = MoneyUtils.formatV2(0L);
        Long _0 = 0L;
        if (StringUtils.isNullOrEmpty(direction) || "asc".equalsIgnoreCase(direction)) {
            end = end.plusDays(1);
            while (start.isBefore(end)) {
                String key = DateUtils.localDateToStr(start, pattern);
                this.addDataToMap(revenueDetailRes, _0VND, _0, key);
                start = start.plusDays(1);
            }
        } else {
            start = start.minusDays(1);
            while (start.isBefore(end)) {
                String key = DateUtils.localDateToStr(end, pattern);
                this.addDataToMap(revenueDetailRes, _0VND, _0, key);
                end = end.minusDays(1);
            }
        }

        return revenueDetailRes;
    }

    private void addDataToMap(Map<String, RevenueDetailItem> revenueDetailRes, String _0VND, Long _0, String key) {
        revenueDetailRes.put(key,
                RevenueDetailItem.builder()
                        .time(key)
                        .orderNumber(_0)
                        .sale(_0)
                        .saleFmt(_0VND)
                        .discount(_0)
                        .discountFmt(_0VND)
                        .refund(_0)
                        .refundFmt(_0VND)
                        .netSale(_0)
                        .netSaleFmt(_0VND)
                        .ship(_0)
                        .shipFmt(_0VND)
                        .total(_0)
                        .totalFmt(_0VND)
                        .productOrderNumber(_0)
                        .productRefundNumber(_0)
                        .productNumber(_0)
                        .build());
    }
}
