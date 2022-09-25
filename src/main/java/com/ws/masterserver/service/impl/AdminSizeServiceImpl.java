package com.ws.masterserver.service.impl;

import com.ws.masterserver.dto.admin.size.create_update.SizeDto;
import com.ws.masterserver.entity.SizeEntity;
import com.ws.masterserver.service.AdminSizeService;
import com.ws.masterserver.utils.base.WsException;
import com.ws.masterserver.utils.base.WsRepository;
import com.ws.masterserver.utils.base.rest.CurrentUser;
import com.ws.masterserver.utils.common.JsonUtils;
import com.ws.masterserver.utils.common.StringUtils;
import com.ws.masterserver.utils.common.UidUtils;
import com.ws.masterserver.utils.common.ValidatorUtils;
import com.ws.masterserver.utils.constants.WsCode;
import com.ws.masterserver.utils.validator.auth.AuthValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Locale;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminSizeServiceImpl implements AdminSizeService {
    private final WsRepository repository;
    private static final String NAME = "Tên";

    @Override
    public Object create(CurrentUser currentUser, SizeDto payload) {
        AuthValidator.checkAdminAndStaff(currentUser);
        log.info("create() payload: {}", JsonUtils.toJson(payload));
        String name = payload.getName();
        if (StringUtils.isNullOrEmpty(name)) {
            ValidatorUtils.validNullOrEmpty(NAME, name);
        } else {
            ValidatorUtils.validLength(NAME, name, 1, 5);
            if (repository.sizeRepository.existsByName(name.trim())) {
                throw new WsException(WsCode.SIZE_EXISTS_NAME);
            }
        }
        String id = UidUtils.generateUid();
        SizeEntity size = SizeEntity.builder()
                .id(id)
                .name(name)
                .active(true)
                .createdDate(new Date())
                .build();
        repository.sizeRepository.save(size);
        return id;
    }

    @Override
    public Object update(CurrentUser currentUser, SizeDto payload) {
        AuthValidator.checkAdminAndStaff(currentUser);
        log.info("update() payload: {}", JsonUtils.toJson(payload));
        String id = payload.getId();
        if (!repository.sizeRepository.existsById(id)) {
            throw new WsException(WsCode.ERROR_NOT_FOUND);
        }
        String name = payload.getName();
        if (StringUtils.isNullOrEmpty(name)) {
            ValidatorUtils.validNullOrEmpty(NAME, name);
        } else {
            ValidatorUtils.validLength(NAME, name, 1, 5);
            if (repository.sizeRepository.existsByNameAndIdNot(name.trim(), id)) {
                throw new WsException(WsCode.SIZE_EXISTS_NAME);
            }
        }
        SizeEntity size = repository.sizeRepository.findById(id).orElseThrow(() -> new WsException(WsCode.INTERNAL_SERVER));
        size.setName(name.toUpperCase(Locale.ROOT).trim());
        repository.sizeRepository.save(size);
        return id;
    }

    @Override
    public Object delete(CurrentUser currentUser, String id) {
        AuthValidator.checkAdmin(currentUser);
        SizeEntity size = repository.sizeRepository.findById(id).orElseThrow(() -> new WsException(WsCode.ERROR_NOT_FOUND));
        repository.sizeRepository.delete(size);
        return true;
    }
}
