package com.ws.masterserver.service.impl;

import com.ws.masterserver.dto.admin.report.discount.DiscountRevenueReq;
import com.ws.masterserver.dto.admin.report.discount.DiscountRevenueRes;
import com.ws.masterserver.excel.discount_revenue.DiscountRevenueExcel;
import com.ws.masterserver.service.AdminDiscountRevenueReportService;
import com.ws.masterserver.utils.base.WsException;
import com.ws.masterserver.utils.base.WsRepository;
import com.ws.masterserver.utils.base.rest.CurrentUser;
import com.ws.masterserver.utils.base.rest.PageData;
import com.ws.masterserver.utils.base.rest.PageReq;
import com.ws.masterserver.utils.common.JsonUtils;
import com.ws.masterserver.utils.constants.WsCode;
import com.ws.masterserver.utils.validator.auth.AuthValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminDiscountRevenueReportServiceImpl implements AdminDiscountRevenueReportService {
    private final WsRepository repository;

    @Override
    public Object get(CurrentUser currentUser, DiscountRevenueReq payload) {
        AuthValidator.checkAdmin(currentUser);
        log.info("get() payload: {}", JsonUtils.toJson(payload));
        return repository.adminDiscountRevenueRepository.get(payload);
    }

    @Override
    public Object export(CurrentUser currentUser, DiscountRevenueReq payload) {
        AuthValidator.checkAdmin(currentUser);
        log.info("get() export: {}", JsonUtils.toJson(payload));
        String type = payload.getExportType();
        if ("ALL".equals(type)) {
            PageReq pageReq = payload.getPageReq();
            pageReq.setPageSize(null);
        }
        PageData res = repository.adminDiscountRevenueRepository.get(payload);
        List<DiscountRevenueRes> data = res.getData();
        if (data.isEmpty()) {
            throw new WsException(WsCode.DATA_EMPTY);
        }
        return DiscountRevenueExcel.export(data);
    }
}
