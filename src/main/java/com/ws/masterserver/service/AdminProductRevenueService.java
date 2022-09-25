package com.ws.masterserver.service;

import com.ws.masterserver.dto.admin.report.product.ProductRevenueReq;
import com.ws.masterserver.utils.base.rest.CurrentUser;

/**
 * @author myname
 */
public interface AdminProductRevenueService {
    Object get(CurrentUser currentUser, ProductRevenueReq payload);

    Object export(CurrentUser currentUser, ProductRevenueReq payload);
}
