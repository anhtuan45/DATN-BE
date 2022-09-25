package com.ws.masterserver.service.impl;

import com.ws.masterserver.dto.admin.user.personal.PersonalDto;
import com.ws.masterserver.dto.customer.user.ProfileDto;
import com.ws.masterserver.service.UserInfoService;
import com.ws.masterserver.utils.base.WsException;
import com.ws.masterserver.utils.base.WsRepository;
import com.ws.masterserver.utils.base.rest.CurrentUser;
import com.ws.masterserver.utils.base.rest.ResData;
import com.ws.masterserver.utils.common.BeanUtils;
import com.ws.masterserver.utils.common.JsonUtils;
import com.ws.masterserver.utils.common.StringUtils;
import com.ws.masterserver.utils.constants.WsCode;
import com.ws.masterserver.utils.validator.auth.AuthValidator;
import com.ws.masterserver.utils.validator.customer.user.ProfileValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserInfoServiceImpl implements UserInfoService {

    private final WsRepository repository;

    @Override
    public Object updateProfile(CurrentUser currentUser, ProfileDto dto) {
        log.info("start updateProfile with payload: {}", JsonUtils.toJson(dto));

        AuthValidator.checkLogin(currentUser);
        com.ws.masterserver.entity.UserEntity user = repository.userRepository.findByIdAndActive(dto.getId(), true);
        if (null == user) {
            throw new WsException(WsCode.INTERNAL_SERVER);
        }
        if (!currentUser.getId().equals(dto.getId())) {
            throw new WsException(WsCode.FORBIDDEN);
        }
        ProfileValidator.validDto(dto);
        PasswordEncoder passwordEncoder = BeanUtils.getBean(PasswordEncoder.class);
        String password = user.getPassword();
        if (!StringUtils.isNullOrEmpty(dto.getNewPassword())) {
            password = passwordEncoder.encode(dto.getNewPassword());
        }
        user.setFirstName(dto.getFirstName().trim())
                .setLastName(dto.getLastName().trim())
                .setPhone(dto.getPhone().trim())
//                .setGender(dto.getGender())
                .setDob(dto.getDob())
                .setPassword(password);
        log.info("updateProfile before save: {}", JsonUtils.toJson(user));
        repository.userRepository.save(user);
        log.info("updateProfile after save: {}", JsonUtils.toJson(user));
        return ResData.ok(user.getId());
    }

    @Override
    public Object personal(CurrentUser currentUser) {
        log.info("start personal");
        AuthValidator.checkLogin(currentUser);
        com.ws.masterserver.entity.UserEntity user = repository.userRepository.findByIdAndActive(currentUser.getId(), true);
        if (null == user) {
            throw new WsException(WsCode.USER_LOCKED);
        }
        return ResData.ok(PersonalDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .dob(user.getDob())
                .email(user.getEmail())
//                .gender(user.getGender())
                .phone(user.getPhone())
                .build());
    }
}
