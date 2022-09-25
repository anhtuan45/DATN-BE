package com.ws.masterserver.service;

import com.ws.masterserver.dto.admin.discount.create.DiscountDto;
import com.ws.masterserver.utils.base.rest.CurrentUser;

/**
 * @author myname
 */
public interface AdminDiscountService {
    Object create(CurrentUser currentUser, DiscountDto payload);

}
