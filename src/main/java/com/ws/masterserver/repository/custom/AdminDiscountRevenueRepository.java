package com.ws.masterserver.repository.custom;

import com.ws.masterserver.dto.admin.report.discount.DiscountRevenueReq;
import com.ws.masterserver.utils.base.rest.PageData;

public interface AdminDiscountRevenueRepository {
    PageData get(DiscountRevenueReq payload);
}
