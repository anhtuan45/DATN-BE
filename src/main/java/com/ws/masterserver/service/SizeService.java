package com.ws.masterserver.service;

import com.ws.masterserver.dto.admin.color.ColorResponseV2;
import com.ws.masterserver.dto.admin.size.SizeResponseV2;
import com.ws.masterserver.dto.customer.product.ColorResponse;
import com.ws.masterserver.dto.customer.size.response.SizeResponse;
import com.ws.masterserver.utils.base.rest.CurrentUser;
import com.ws.masterserver.utils.base.rest.ResData;

import java.util.List;

public interface SizeService {
    List<SizeResponse> getAllSize();


    ResData<String> create(CurrentUser currentUser, SizeResponseV2 res);

    ResData<String> delete(CurrentUser currentUser, String id);
    Object changeStatus(CurrentUser currentUser, String id);

    ResData<String> update(CurrentUser currentUser, SizeResponseV2 res);

    ResData<List<SizeResponseV2>> getAllSizeV2();
    Object detail(CurrentUser currentUser, String id);

    Object noPage();

}
