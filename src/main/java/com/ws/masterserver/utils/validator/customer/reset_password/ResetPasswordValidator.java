package com.ws.masterserver.utils.validator.customer.reset_password;

import com.ws.masterserver.dto.customer.reset_password.ResetPasswordDto;
import com.ws.masterserver.entity.ResetTokenEntity;
import com.ws.masterserver.entity.UserEntity;
import com.ws.masterserver.utils.base.WsException;
import com.ws.masterserver.utils.base.WsRepository;
import com.ws.masterserver.utils.common.BeanUtils;
import com.ws.masterserver.utils.common.DateUtils;
import com.ws.masterserver.utils.common.StringUtils;
import com.ws.masterserver.utils.common.ValidatorUtils;
import com.ws.masterserver.utils.constants.WsCode;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;

public class ResetPasswordValidator {
    private static final String PASSWORD = "Mật khẩu";
    public static void validDto(ResetPasswordDto payload) {
        if (StringUtils.isNullOrEmpty(payload.getToken())) {
            throw new WsException(WsCode.RESET_PASSWORD_FAILED);
        }
        ValidatorUtils.validNullOrEmpty(PASSWORD, payload.getPassword());
        ValidatorUtils.validLength(PASSWORD, payload.getPassword(), 6, 250);
    }

    public static void validConstraint(WsRepository repository, ResetPasswordDto payload) {
        ResetTokenEntity token = repository.resetTokenRepository.findByTokenActive(payload.getToken(), true);
        if (token == null) {
            throw new WsException(WsCode.RESET_PASSWORD_FAILED);
        }

        LocalDateTime  createdDate = DateUtils.dateToLocalDateTime(token.getCreatedDate());
        createdDate = createdDate.plusMinutes(10L);
        if (createdDate.isBefore(LocalDateTime.now())) {
            throw new WsException(WsCode.RESET_PASSWORD_FAILED);
        }
        BCryptPasswordEncoder passwordEncoder = BeanUtils.getBean(BCryptPasswordEncoder.class);
        UserEntity user = repository.userRepository.findByIdAndActive(token.getUserId(), true);

        if (user == null) {
            throw new WsException(WsCode.RESET_PASSWORD_FAILED);
        }
        if (passwordEncoder.matches(payload.getPassword(), user.getPassword())) {
            throw new WsException(WsCode.NEW_PASS_NOT_SAME_OLD);
        }
    }
}
