package com.ws.masterserver.service.impl;

import com.ws.masterserver.dto.admin.user.search.CustomerTypeDto;
import com.ws.masterserver.dto.admin.user.search.UserReq;
import com.ws.masterserver.dto.admin.user.search.UserRes;
import com.ws.masterserver.entity.CustomerTypeEntity;
import com.ws.masterserver.entity.UserEntity;
import com.ws.masterserver.service.AdminUserService;
import com.ws.masterserver.utils.base.WsException;
import com.ws.masterserver.utils.base.WsRepository;
import com.ws.masterserver.utils.base.rest.CurrentUser;
import com.ws.masterserver.utils.base.rest.PageData;
import com.ws.masterserver.utils.base.rest.ResData;
import com.ws.masterserver.utils.common.DateUtils;
import com.ws.masterserver.utils.common.PageableUtils;
import com.ws.masterserver.utils.common.StringUtils;
import com.ws.masterserver.utils.constants.WsCode;
import com.ws.masterserver.utils.constants.enums.RoleEnum;
import com.ws.masterserver.utils.validator.auth.AuthValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminUserServiceImpl implements AdminUserService {
    private final WsRepository repository;

    @Override
    public Object search(CurrentUser currentUser, UserReq req) {
        AuthValidator.checkAdminAndStaff(currentUser);
        RoleEnum role = RoleEnum.from(req.getRole());
        if (role == null && !StringUtils.isNullOrEmpty(req.getRole())) {
            throw new WsException(WsCode.INTERNAL_SERVER);
        }
        Pageable pageable = PageableUtils.getPageable(req.getPageReq());
        String text = StringUtils.isNullOrEmpty(req.getTextSearch()) ? "" : req.getTextSearch();
        String textSearch = "%" + text.trim().toUpperCase(Locale.ROOT) + "%";
        Page<UserEntity> userPage = repository.userRepository.search(textSearch, req.getActive(), role, req.getCustomerTypeId(), pageable);
        if (userPage.isEmpty()) {
            return PageData.setEmpty(req.getPageReq());
        }
        return PageData.setResult(userPage.getContent().stream().map(this::getUserResFromUserEntity).collect(Collectors.toList()),
                userPage.getNumber(),
                userPage.getSize(),
                userPage.getTotalElements());

    }

    @Override
    public Object detail(CurrentUser currentUser, String id) {
        AuthValidator.checkAdminAndStaff(currentUser);
        UserEntity user = repository.userRepository.findById(id).orElse(null);
        if (user == null) {
            throw new WsException(WsCode.ERROR_NOT_FOUND);
        }
        UserRes userRes = this.getUserResFromUserEntity(user);
        List<CustomerTypeDto> customerTypeDtos = repository.customerTypeRepository.findByCustomerId(id);
        userRes.setCustomerTypes(customerTypeDtos);
        return userRes;
    }

    private UserRes getUserResFromUserEntity(UserEntity user) {
        RoleEnum roleEnum = user.getRole();
        Date dob = user.getDob();
        return UserRes.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .combinationName(user.getFirstName() + " " + user.getLastName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .role(roleEnum.name())
                .roleName(roleEnum.getName())
                .active(user.getActive())
                .activeName(user.getActive() ? "Hoạt động" : "Ngừng hoạt động")
                .activeClazz(user.getActive() ? "success" : "danger")
                .createdDate(user.getCreatedDate())
                .createdDateFmt(user.getCreatedDate() == null ? null : DateUtils.toStr(user.getCreatedDate(), DateUtils.F_DDMMYYYYHHMM))
                .gender(user.getGender())
                .dob(dob)
                .dobMil(dob == null ? new Date().getTime() : dob.getTime())
                .dobFmt(dob == null ? null : DateUtils.toStr(dob, DateUtils.F_DDMMYYYY))
                .orderNumber(repository.orderRepository.countByUserId(user.getId()))
                .build();
    }

}
