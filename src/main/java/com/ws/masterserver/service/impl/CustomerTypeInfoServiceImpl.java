package com.ws.masterserver.service.impl;

import com.ws.masterserver.dto.admin.discount.no_page.CustomerTypeDto;
import com.ws.masterserver.service.CustomerTypeInfoService;
import com.ws.masterserver.utils.base.WsRepository;
import com.ws.masterserver.utils.base.rest.CurrentUser;
import com.ws.masterserver.utils.base.rest.ResData;
import com.ws.masterserver.utils.constants.enums.CustomerTypeEnums;
import com.ws.masterserver.utils.validator.auth.AuthValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerTypeInfoServiceImpl implements CustomerTypeInfoService {
    private final WsRepository repository;

    @Override
    public Object noPage(CurrentUser currentUser) {
        AuthValidator.checkLogin(currentUser);
        return repository.customerTypeRepository.noPage().stream().map(o ->
                CustomerTypeDto.builder()
                        .id(o.getId())
                        .name(o.getName())
                        .customerNumber(repository.customerGroupRepository.countByCustomerTypeId(o.getId()))
                        .build()).collect(Collectors.toList());
    }
}
