package com.ws.masterserver.service.impl;

import com.ws.masterserver.service.AdminDiscountTypeService;
import com.ws.masterserver.utils.base.WsRepository;
import com.ws.masterserver.utils.base.rest.CurrentUser;
import com.ws.masterserver.utils.validator.auth.AuthValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminDiscountTypeServiceImpl implements AdminDiscountTypeService {
    private final WsRepository repository;

    @Override
    public Object noPage(CurrentUser currentUser) {
        AuthValidator.checkLogin(currentUser);

        return null;
    }
}
