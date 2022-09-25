package com.ws.masterserver.service;

import com.ws.masterserver.dto.admin.color.ColorResponseV2;
import com.ws.masterserver.dto.admin.color.search.ColorReq;
import com.ws.masterserver.dto.customer.product.ColorResponse;
import com.ws.masterserver.utils.base.enum_dto.ColorDto;
import com.ws.masterserver.utils.base.rest.CurrentUser;
import com.ws.masterserver.utils.base.rest.ResData;

import java.util.List;

public interface ColorService {
    ResData<String> create(CurrentUser currentUser, ColorDto dto);

    ResData<String> delete(CurrentUser currentUser, String id);
    ResData<String> changeStatus(CurrentUser currentUser, String id);

    ResData<String> update(CurrentUser currentUser, ColorDto dto);

    ResData<List<ColorResponse>> getListColor();

    ResData<List<ColorResponseV2>> getListColorV2();
    Object detail(CurrentUser currentUser, String id);
    Object noPage();
    Object search(CurrentUser currentUser, ColorReq payload);
}
