package com.ws.masterserver.service.impl;

import com.ws.masterserver.dto.admin.user.info.UserDto;
import com.ws.masterserver.entity.CustomerGroupEntity;
import com.ws.masterserver.entity.UserEntity;
import com.ws.masterserver.service.AdminUserInfoService;
import com.ws.masterserver.utils.base.WsException;
import com.ws.masterserver.utils.base.WsRepository;
import com.ws.masterserver.utils.base.rest.CurrentUser;
import com.ws.masterserver.utils.base.rest.ResData;
import com.ws.masterserver.utils.common.*;
import com.ws.masterserver.utils.constants.WsCode;
import com.ws.masterserver.utils.constants.enums.RoleEnum;
import com.ws.masterserver.utils.validator.auth.AuthValidator;
import com.ws.masterserver.utils.validator.admin.user.AdminUserValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@SuppressWarnings("deprecation")
public class AdminUserInfoServiceImpl implements AdminUserInfoService {
    private final WsRepository repository;

    @Override
    public Object create(CurrentUser currentUser, UserDto dto) {
        log.info("AdminUserInfoServiceImpl create start");
        AuthValidator.checkAdmin(currentUser);
        AdminUserValidator.validCreate(dto);
        PasswordEncoder passwordEncoder = BeanUtils.getBean(PasswordEncoder.class);
        final String id = UidUtils.generateUid();
        RoleEnum role = RoleEnum.valueOf(dto.getRole());
        UserEntity user = UserEntity.builder()
                .id(id)
                .firstName(dto.getFirstName().trim())
                .lastName(dto.getLastName().trim())
                .email(dto.getEmail().trim())
                .phone(dto.getPhone().trim())
                .password(passwordEncoder.encode(dto.getPassword()))
                .active(true)
//                .gender(dto.getGender())
                .role(role)
                .dob(dto.getDob())
                .build();
        this.saveUser(user);
        if (RoleEnum.ROLE_CUSTOMER.equals(role)) {
            this.saveCustomerType(id, dto.getCustomerTypeIds());
        }
        return ResData.ok(user.getId());
    }

    private void saveCustomerType(String userId, List<String> list) {
        log.info("saveCustomerType() userId: {}. list: {}", userId, JsonUtils.toJson(list));
        if (list.isEmpty()) {
            return;
        }
        for (String customerTypeId : list) {
            if (!repository.customerTypeRepository.existsById(customerTypeId)) {
                throw new WsException(WsCode.ERROR_NOT_FOUND);
            }
            repository.customerGroupRepository.save(CustomerGroupEntity.builder()
                    .id(UidUtils.generateUid())
                    .customerTypeId(customerTypeId)
                    .userId(userId)
                    .build());
        }
    }

    @Override
    @Transactional
    public Object update(CurrentUser currentUser, UserDto dto) {
        log.info("AdminUserInfoServiceImpl update start dto: {}", JsonUtils.toJson(dto));
        AuthValidator.checkAdmin(currentUser);
        AdminUserValidator.validUpdate(dto);
        UserEntity user = repository.userRepository.findById(dto.getId()).orElseThrow(() -> new WsException(WsCode.ERROR_NOT_FOUND));
        RoleEnum roleOld = user.getRole();
        RoleEnum roleNow = RoleEnum.valueOf(dto.getRole());
        if (RoleEnum.ROLE_CUSTOMER.equals(roleOld)) {
            repository.customerGroupRepository.deleteByUserId(user.getId());
        }
        user.setFirstName(dto.getFirstName().trim())
                .setLastName(dto.getLastName().trim())
                .setPhone(dto.getPhone().trim())
                .setRole(roleNow)
                .setDob(dto.getDob());
        this.saveUser(user);
        if (RoleEnum.ROLE_CUSTOMER.equals(roleNow)) {
            this.saveCustomerType(user.getId(), dto.getCustomerTypeIds());
        }
        return user.getId();
    }

    @Override
    @Transactional
    public Object changeStatus(CurrentUser currentUser, String id) {
        AuthValidator.checkAdmin(currentUser);
        UserEntity user = repository.userRepository.findById(id).orElseThrow(() -> new WsException(WsCode.USER_NOT_FOUND));
        if (currentUser.getId().equals(user.getId())) {
            throw new WsException(WsCode.DONT_CHANGE_YOURSELF);
        }
        if (RoleEnum.ROLE_ADMIN.equals(user.getRole())) {
            throw new WsException(WsCode.FORBIDDEN);
        }
        user.setActive(!user.getActive());
        this.saveUser(user);
        return ResData.ok(user.getId());
    }

    @Override
    @Transactional
    public Object delete(CurrentUser currentUser, String id) {
        AuthValidator.checkAdmin(currentUser);
        UserEntity user = repository.userRepository.findById(id).orElseThrow(() -> new WsException(WsCode.USER_NOT_FOUND));
        if (currentUser.getId().equals(user.getId())) {
            throw new WsException(WsCode.DONT_CHANGE_YOURSELF);
        }
        if (RoleEnum.ROLE_ADMIN.equals(user.getRole())) {
            throw new WsException(WsCode.FORBIDDEN);
        }
        repository.userRepository.delete(user);
        return true;
    }

    private void saveUser(UserEntity user) {
        log.info("AdminUserInfoServiceImpl user before save: {}", JsonUtils.toJson(user));
        repository.userRepository.save(user);
        log.info("AdminUserInfoServiceImpl user after save: {}", JsonUtils.toJson(user));
    }
}
