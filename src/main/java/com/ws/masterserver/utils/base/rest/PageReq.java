package com.ws.masterserver.utils.base.rest;

import com.ws.masterserver.utils.common.StringUtils;
import com.ws.masterserver.utils.constants.WsConst;
import lombok.Data;

@Data
public class PageReq {

    //số trang, mặc định bắt đầu bằng 0
    private Integer page = WsConst.Values.PAGE_DEFAULT;

    //số phần tử tối đá trong 1 trang
    private Integer pageSize = WsConst.Values.PAGE_SIZE_DEFAULT;

    //sắp xếp theo thuộc tính nào?
    private String sortField;

    //sắp xếp theo thứ tự gì: asc - desc
    private String sortDirection = WsConst.Values.SORT_DIRECTION_DEFAULT;

//    public PageReq(Integer page, Integer pageSize, String sortField, String sortDirection) {
//        if (page == null || page < 1) {
//            page = WsConst.Values.PAGE_DEFAULT;
//        }
//        if (pageSize == null || pageSize < 1) {
//            pageSize = WsConst.Values.PAGE_SIZE_DEFAULT;
//        }
//        if (StringUtils.isNullOrEmpty(sortField)) {
//            sortField = WsConst.Values.SORT_FIELD_DEFAULT;
//        }
//        if (StringUtils.isNullOrEmpty(sortDirection)) {
//            sortDirection = WsConst.Values.SORT_DIRECTION_DEFAULT;
//        }
//        this.page = page;
//        this.pageSize = pageSize;
//        this.sortField = sortField;
//        this.sortDirection = sortDirection;
//    }
}
