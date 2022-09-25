package com.ws.masterserver.utils.common;

import com.ws.masterserver.utils.base.rest.PageReq;
import com.ws.masterserver.utils.constants.WsConst;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;

public class PageableUtils {

    private static final List<String> fieldIgnoreCases = Arrays.asList(
            "name",
            "address",
            "province",
            "firstName",
            "lastName",
            "email"
    );
    
    public static Pageable getPageable(PageReq req) {
        getPageReq(req);
        Sort.Direction direction = req.getSortDirection().equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort.Order order;
        if (fieldIgnoreCases.stream().anyMatch(item -> req.getSortField().equals(item))) {
            order = new Sort.Order(direction, req.getSortField()).ignoreCase();
        } else {
            order = new Sort.Order(direction, req.getSortField());
        }
        return PageRequest.of(req.getPage(), req.getPageSize(), Sort.by(order));
    }

    public static void getPageReq(PageReq req) {
        if (null == req.getPage() || req.getPage() < 0) {
            req.setPage(WsConst.Values.PAGE_DEFAULT);
        }
        if (null == req.getPageSize() || req.getPageSize() < 0) {
            req.setPageSize(WsConst.Values.PAGE_SIZE_DEFAULT);
        }
        if (StringUtils.isNullOrEmpty(req.getSortField())) {
            req.setSortField(WsConst.Values.SORT_FIELD_DEFAULT);
        }
        if (StringUtils.isNullOrEmpty(req.getSortDirection())) {
            req.setSortDirection(WsConst.Values.SORT_DIRECTION_DEFAULT);
        }
    }

    public static void getPageReq(PageReq pageReq, String sortFieldCustom, String sortDirectionCustom) {
        pageReq.setSortField(sortFieldCustom);
        pageReq.setSortDirection(sortDirectionCustom);
        getPageReq(pageReq);
    }

    public static void getPageReq(PageReq pageReq, Integer page, Integer pageSize, String sortFieldCustom, String sortDirectionCustom) {
        pageReq.setPage(page);
        pageReq.setPageSize(pageSize);
        getPageReq(pageReq, sortFieldCustom, sortDirectionCustom);
        getPageReq(pageReq);
    }

    public static void getPageReq(PageReq pageReq, Integer page, Integer pageSize) {
        pageReq.setPage(page);
        pageReq.setPageSize(pageSize);
        getPageReq(pageReq);
    }
}
