package com.ws.masterserver.utils.validator.auth;

import com.ws.masterserver.utils.base.WsException;
import com.ws.masterserver.utils.common.JsonUtils;
import com.ws.masterserver.utils.constants.WsCode;
import com.ws.masterserver.utils.constants.enums.RoleEnum;
import com.ws.masterserver.utils.base.rest.CurrentUser;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

/**
 * Luôn dùng class này trong các api phân quyền
 */
@Slf4j
public class AuthValidator {
    private AuthValidator() {
    }

    public static void checkAdmin(CurrentUser currentUser) {
        if (!currentUser.getRole().equals(RoleEnum.ROLE_ADMIN)) {
            throw new WsException(WsCode.FORBIDDEN);
        }
    }

    public static void checkAdminAndStaff(CurrentUser currentUser) {
        checkRole(currentUser, RoleEnum.ROLE_ADMIN, RoleEnum.ROLE_STAFF);
    }

    public static void checkCustomerAndStaff(CurrentUser currentUser) {
        checkRole(currentUser, RoleEnum.ROLE_CUSTOMER, RoleEnum.ROLE_STAFF);
    }

    public static void checkStaff(CurrentUser currentUser) {
        checkLogin(currentUser);
        if (!currentUser.getRole().equals(RoleEnum.ROLE_STAFF)) {
            throw new WsException(WsCode.FORBIDDEN);
        }
    }

    public static void checkCustomer(CurrentUser currentUser) {
        checkLogin(currentUser);
        if (!RoleEnum.ROLE_CUSTOMER.equals(currentUser.getRole())) {
            throw new WsException(WsCode.FORBIDDEN);
        }
    }

    public static void checkRole(CurrentUser currentUser, RoleEnum roleAvailable) {
        checkLogin(currentUser);
        if (!currentUser.getRole().equals(roleAvailable)) {
            throw new WsException(WsCode.FORBIDDEN);
        }
    }

    public static void checkRole(CurrentUser currentUser, RoleEnum... roleAvailableList) {
        checkLogin(currentUser);
        if (!Arrays.stream(roleAvailableList).anyMatch(role -> role.equals(currentUser.getRole()))) {
            throw new WsException(WsCode.FORBIDDEN);
        }

    }

    public static void checkLogin(CurrentUser currentUser) {
        log.info("checkLogin() with currentUser: {}", JsonUtils.toJson(currentUser));
        if (currentUser == null) {
            throw new WsException(WsCode.FORBIDDEN);
        }
    }
}
