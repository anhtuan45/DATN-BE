package com.ws.masterserver.service.impl;

import com.ws.masterserver.dto.admin.report.product.ProductRevenueReq;
import com.ws.masterserver.dto.admin.report.product.ProductRevenueRes;
import com.ws.masterserver.excel.product_revenue.ProductRevenueExcel;
import com.ws.masterserver.service.AdminProductRevenueService;
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
@SuppressWarnings("unchecked")
public class AdminProductRevenueServiceImpl implements AdminProductRevenueService {

    private final WsRepository repository;

    private static final Integer PAGE = 0;
    private static final Integer PAGE_SIZE = 999999999;
    private static final String SORT_FIELD = "productName";

    @Override
    public Object get(CurrentUser currentUser, ProductRevenueReq payload) {
        AuthValidator.checkAdmin(currentUser);
        return repository.productRevenueRepository.get(payload);
    }

    @Override
    public Object export(CurrentUser currentUser, ProductRevenueReq payload) {
        AuthValidator.checkAdmin(currentUser);
        if (payload.getExportType() == null || "ALL".equalsIgnoreCase(payload.getExportType())) {
            PageableUtils.getPageReq(payload.getPageReq(), PAGE, PAGE_SIZE, SORT_FIELD, payload.getPageReq().getSortDirection());
        } else {
            PageableUtils.getPageReq(payload.getPageReq(), PAGE, PAGE_SIZE);
        }
        PageData res = (PageData) repository.productRevenueRepository.get(payload);
        List<ProductRevenueRes> data =  res.getData();
        if (data.isEmpty()) {
            throw new WsException(WsCode.DATA_EMPTY);
        }
        return ProductRevenueExcel.export(data);
    }

}
