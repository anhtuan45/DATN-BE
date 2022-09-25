package com.ws.masterserver.service;

import com.ws.masterserver.utils.base.enum_dto.MaterialDto;
import com.ws.masterserver.utils.base.rest.CurrentUser;
import com.ws.masterserver.utils.base.rest.ResData;

public interface MaterialService {
    ResData<String> create(CurrentUser currentUser, MaterialDto dto);

    ResData<String> delete(CurrentUser currentUser, MaterialDto dto);

    ResData<String> update(CurrentUser currentUser, MaterialDto dto);

    Object noPage();
}
