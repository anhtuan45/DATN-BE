package com.ws.masterserver.service;

import com.ws.masterserver.dto.admin.size.create_update.SizeDto;
import com.ws.masterserver.utils.base.rest.CurrentUser;
import com.ws.masterserver.utils.base.rest.ResData;
import org.springframework.http.ResponseEntity;

@SuppressWarnings("deprecation")
public interface AdminSizeService {
    Object create(CurrentUser currentUser, SizeDto payloadd);

    Object update(CurrentUser currentUser, SizeDto payload);

    Object delete(CurrentUser currentUser, String id);
}
