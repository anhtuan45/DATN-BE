package com.ws.masterserver.repository.custom;

import com.ws.masterserver.dto.admin.report.product.ProductRevenueReq;
import com.ws.masterserver.utils.base.rest.CurrentUser;

/**
 * @author myname
 */
public interface ProductRevenueRepository {
    Object get(ProductRevenueReq payload);
}
