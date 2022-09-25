package com.ws.masterserver.service.impl;

import com.ws.masterserver.dto.admin.report.customer.CustomerReportReq;
import com.ws.masterserver.dto.admin.report.customer.CustomerReportRes;
import com.ws.masterserver.excel.customer_revenue.CustomerRevenueExcel;
import com.ws.masterserver.service.CustomerReportService;
import com.ws.masterserver.utils.base.WsException;
import com.ws.masterserver.utils.base.WsRepository;
import com.ws.masterserver.utils.base.rest.CurrentUser;
import com.ws.masterserver.utils.base.rest.PageData;
import com.ws.masterserver.utils.common.PageableUtils;
import com.ws.masterserver.utils.constants.WsCode;
import com.ws.masterserver.utils.validator.auth.AuthValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerReportServiceImpl implements CustomerReportService {

    private final WsRepository repository;

    private static final Integer PAGE = 0;
    private static final Integer PAGE_SIZE = 999999999;
    private static final String SORT_FIELD = "userName";

    @Override
    public Object get(CurrentUser currentUser,CustomerReportReq req) {

        AuthValidator.checkAdmin(currentUser);
        return repository.customerRevenueRepository.get(req);

    }

    @Override
    public Object export(CurrentUser currentUser, CustomerReportReq payload) {
        AuthValidator.checkAdmin(currentUser);
        if (payload.getExportType() == null || "ALL".equalsIgnoreCase(payload.getExportType())) {
            PageableUtils.getPageReq(payload.getPageReq(), PAGE, PAGE_SIZE, SORT_FIELD, payload.getPageReq().getSortDirection());
        } else {
            PageableUtils.getPageReq(payload.getPageReq(), PAGE, PAGE_SIZE);
        }
        PageData res = (PageData) repository.customerRevenueRepository.get(payload);
        List<CustomerReportRes> data =  res.getData();
        if (data.isEmpty()) {
            throw new WsException(WsCode.DATA_EMPTY);
        }
        return CustomerRevenueExcel.export(data);
    }
}
