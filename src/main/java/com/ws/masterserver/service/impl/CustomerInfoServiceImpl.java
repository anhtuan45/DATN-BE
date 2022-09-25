package com.ws.masterserver.service.impl;

import com.ws.masterserver.dto.admin.discount.no_page.CustomerDto;
import com.ws.masterserver.service.CustomerInfoService;
import com.ws.masterserver.utils.base.WsRepository;
import com.ws.masterserver.utils.base.rest.CurrentUser;
import com.ws.masterserver.utils.validator.auth.AuthValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerInfoServiceImpl implements CustomerInfoService {
    private final WsRepository repository;
    @Override
    public Object noPage(CurrentUser currentUser) {
        AuthValidator.checkLogin(currentUser);
        return repository.userRepository.customerNoPage().stream().map(o ->
                CustomerDto.builder()
                        .id(o.getId())
                        .name(o.getFirstName() + " " + o.getLastName())
                        .email(o.getEmail())
                        .build()).collect(Collectors.toList());
    }
}
