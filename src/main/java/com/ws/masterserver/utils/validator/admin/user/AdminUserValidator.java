package com.ws.masterserver.utils.validator.admin.user;

import com.ws.masterserver.dto.admin.user.info.UserDto;
import com.ws.masterserver.utils.base.WsException;
import com.ws.masterserver.utils.base.WsRepository;
import com.ws.masterserver.utils.common.BeanUtils;
import com.ws.masterserver.utils.common.ValidatorUtils;
import com.ws.masterserver.utils.constants.WsCode;
import com.ws.masterserver.utils.constants.field.UserFields;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AdminUserValidator {
    private AdminUserValidator() {
    }

    public static void validCreate(UserDto dto) {
        log.info("AdminUserValidator validCreate start");
        validCreateOrUpdate(dto);
        validOnlyCreate(dto);
        WsRepository repository = BeanUtils.getBean(WsRepository.class);
        validEmailMustBeUnique(repository, dto.getEmail().trim());
        validPhoneMustBeUnique(repository, dto.getPhone().trim());
    }

    public static void validOnlyCreate(UserDto dto) {
        ValidatorUtils.validNullOrEmpty(UserFields.EMAIL, dto.getEmail());
        ValidatorUtils.validLength(UserFields.EMAIL, dto.getEmail(), 6, 250);
        ValidatorUtils.validEmail(UserFields.EMAIL, dto.getEmail());
        ValidatorUtils.validNullOrEmpty(UserFields.PASSWORD, dto.getPassword());
        ValidatorUtils.validLength(UserFields.PASSWORD, dto.getPassword(), 6, 100);
    }

    public static void validPhoneMustBeUnique(WsRepository repository, String phone) {
        log.info("AdminUserValidator validPhoneMustBeUnique start");
        if (repository.userRepository.existsByPhone(phone.trim())) {
            throw new WsException(WsCode.PHONE_EXISTS);
        }
    }

    public static void validEmailMustBeUnique(WsRepository repository, String email) {
        log.info("AdminUserValidator validEmailMustBeUnique start");
        if (repository.userRepository.existsByEmailIgnoreCase(email.trim())) {
            throw new WsException(WsCode.EMAIL_EXISTS);
        }
    }

    /**
     * khi update chỉ được thay đổi 1 số trường. các trường khác phải giữ nguyên
     */
    public static void validUpdate(UserDto dto) {
        log.info("AdminUserValidator validUpdate start");
        WsRepository repository = BeanUtils.getBean(WsRepository.class);
        validExists(repository, dto.getId());
        validCreateOrUpdate(dto);
        validPhoneMustBeUnique(repository, dto.getPhone(), dto.getId());
    }

    public static void validPhoneMustBeUnique(WsRepository repository, String phone, String id) {
        log.info("AdminUserValidator validPhoneMustBeUnique start");
        if (repository.userRepository.existsByPhoneAndIdNot(phone.trim(), id)) {
            throw new WsException(WsCode.PHONE_EXISTS);
        }
    }

    public static void validCreateOrUpdate(UserDto dto) {
        ValidatorUtils.validNullOrEmpty(UserFields.FIRST_NAME, dto.getFirstName());
        ValidatorUtils.validLength(UserFields.FIRST_NAME, dto.getFirstName(), 100, true);
        ValidatorUtils.validOnlyCharacter(UserFields.FIRST_NAME, dto.getFirstName());
        ValidatorUtils.validNullOrEmpty(UserFields.LAST_NAME, dto.getLastName());
        ValidatorUtils.validLength(UserFields.LAST_NAME, dto.getLastName(), 100, true);
        ValidatorUtils.validOnlyCharacter(UserFields.LAST_NAME, dto.getLastName());
        ValidatorUtils.validNullOrEmpty(UserFields.PHONE, dto.getPhone());
        ValidatorUtils.validPhone(UserFields.PHONE, dto.getPhone());
        ValidatorUtils.validRole(UserFields.ROLE, dto.getRole());
//        ValidatorUtils.validBooleanType(UserFields.GENDER, dto.getGender());
        ValidatorUtils.validAgeBetween(UserFields.DOB, dto.getDob(), 14, 115);
    }

    public static void validExists(WsRepository repository, String id) {
        if (!repository.userRepository.existsByIdAndActive(id, true)) {
            throw new WsException(WsCode.USER_NOT_FOUND);
        }
    }

    public static void validEmailMustBeUnique(WsRepository repository, String email, String id) {
        log.info("AdminUserValidator validEmailMustBeUnique start");
        if (repository.userRepository.existsByEmailIgnoreCaseAndIdNot(email.trim(), id)) {
            throw new WsException(WsCode.EMAIL_EXISTS);
        }
    }
}
