package com.ws.masterserver.service.impl;

import com.ws.masterserver.dto.admin.size.SizeResponseV2;
import com.ws.masterserver.dto.customer.size.response.SizeResponse;
import com.ws.masterserver.entity.SizeEntity;
import com.ws.masterserver.service.SizeService;
import com.ws.masterserver.utils.base.WsException;
import com.ws.masterserver.utils.base.WsRepository;
import com.ws.masterserver.utils.base.enum_dto.SizeDto;
import com.ws.masterserver.utils.base.rest.CurrentUser;
import com.ws.masterserver.utils.base.rest.ResData;
import com.ws.masterserver.utils.common.JsonUtils;
import com.ws.masterserver.utils.common.StringUtils;
import com.ws.masterserver.utils.common.UidUtils;
import com.ws.masterserver.utils.constants.WsCode;
import com.ws.masterserver.utils.validator.auth.AuthValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SizeServiceImpl implements SizeService {

    private final WsRepository repository;

    @Override
    @Deprecated
    public List<SizeResponse> getAllSize() {
        List<SizeResponse> size = repository.sizeRepository.getAllSize();

        return size;
    }

    //    =================
    @Override
    public ResData<String> create(CurrentUser currentUser, SizeResponseV2 res) {
        AuthValidator.checkAdmin(currentUser);
        if (Boolean.TRUE.equals(repository.sizeRepository.findByName(res.getName()))) {
            throw new WsException(WsCode.SIZE_EXISTS_NAME);
        }
        if (StringUtils.isNullOrEmpty(res.getCode())) {
            throw new WsException(WsCode.REQUIRED_FIELD);
        }
        if (StringUtils.isNullOrEmpty(res.getName())) {
            throw new WsException(WsCode.REQUIRED_FIELD);
        }
        SizeEntity size = SizeEntity.builder()
                .id(UidUtils.generateUid())
                .name(res.getName().trim())
                .active(Boolean.TRUE)
                .build();

        repository.sizeRepository.save(size);
        log.info("create finished at {} with response: {}", new Date(), JsonUtils.toJson(size));
        return new ResData<>(size.getId(), WsCode.OK);
    }

    @Override
    public ResData<String> delete(CurrentUser currentUser, String id) {
        AuthValidator.checkAdmin(currentUser);
        if (id == null || Boolean.FALSE.equals(repository.sizeRepository.findByIdAndActive(id, Boolean.TRUE))) {
            throw new WsException(WsCode.SIZE_NOT_FOUND);
        }
        SizeEntity size = repository.sizeRepository.findByIdAndActive(id, Boolean.TRUE);
        size.setActive(Boolean.FALSE);
        repository.sizeRepository.save(size);
        log.info("delete finished at {} with response: {}", new Date(), JsonUtils.toJson(size));
        return new ResData<>(size.getId(), WsCode.OK);
    }

    @Override
    public Object changeStatus(CurrentUser currentUser, String id) {
        AuthValidator.checkAdmin(currentUser);
        SizeEntity size = repository.sizeRepository.findById(id).orElse(null);
        if (size == null) {
            throw new WsException(WsCode.SIZE_NOT_FOUND);
        }
        size.setActive(!size.getActive());
        repository.sizeRepository.save(size);
        return id;
    }

    @Override
    public ResData<String> update(CurrentUser currentUser, SizeResponseV2 res) {
        AuthValidator.checkAdmin(currentUser);
        if (res.getId() == null || Boolean.FALSE.equals(repository.sizeRepository.existsByIdAndActive(res.getId(), Boolean.TRUE))) {
            throw new WsException(WsCode.SIZE_NOT_FOUND);
        }
        SizeEntity size = repository.sizeRepository.findByIdAndActive(res.getId(), Boolean.TRUE);
        size.setName(res.getName().trim());
        repository.sizeRepository.save(size);
        log.info("update finished at {} with response: {}", new Date(), JsonUtils.toJson(size));
        return new ResData<>(size.getId(), WsCode.OK);
    }

    @Override
    public ResData<List<SizeResponseV2>> getAllSizeV2() {
//        List<SizeResponseV2> size = repository.sizeRepository.findAllSizeV2();
        return null;
    }

    @Override
    public Object detail(CurrentUser currentUser, String id) {
        AuthValidator.checkAdminAndStaff(currentUser);
        SizeEntity size = repository.sizeRepository.findById(id).orElseThrow(() -> new WsException(WsCode.ERROR_NOT_FOUND));
        return SizeResponseV2.builder()
                .id(size.getId())
                .name(size.getName())
                .active(size.getActive())
                .build();
    }

    @Override
    public Object noPage() {
        return repository.sizeRepository.findAll();
    }

}

