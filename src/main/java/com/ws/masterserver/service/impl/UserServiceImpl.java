package com.ws.masterserver.service.impl;

import com.ws.masterserver.dto.customer.user.RegisterDto;
import com.ws.masterserver.utils.base.WsException;
import com.ws.masterserver.utils.base.WsRepository;
import com.ws.masterserver.service.UserService;
import com.ws.masterserver.utils.common.BeanUtils;
import com.ws.masterserver.utils.common.StringUtils;
import com.ws.masterserver.utils.constants.WsCode;
import com.ws.masterserver.utils.constants.WsConst;
import com.ws.masterserver.utils.validator.customer.user.RegisterValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    private final WsRepository repository;

    @Override
    public UserDetails loadUserByUsername(String value) throws UsernameNotFoundException {
        com.ws.masterserver.dto.customer.user.UserDto userDto = repository.userRepository.findUserDtoByEmail(value, Boolean.TRUE);
        if (userDto == null) {
            throw new UsernameNotFoundException(String.format(WsConst.Messages.NOT_FOUND, WsConst.Nouns.USER_VI.toLowerCase(Locale.ROOT)));
        }

        return User.builder()
                .username(userDto.getEmail())
                .password(userDto.getPassword())
                .authorities(Arrays.asList(new SimpleGrantedAuthority(userDto.getRole().name())))
                .build();
    }

    /**
     * Khách hàng tự đăng ký tài khoản cho mình
     */
    @Override
    public Object registerCustomer(RegisterDto body) {
        RegisterValidator.validateRegisterDto(body);
        return null;
    }

    @Override
    public Object getCurrentUserProfile() {
        WsRepository repository = BeanUtils.getBean(WsRepository.class);
        org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String id = authentication.getPrincipal().toString().replace("\"", "").trim();
        if (StringUtils.isNullOrEmpty(id)) {
            throw new WsException(WsCode.USER_NOT_FOUND);
        }

        com.ws.masterserver.dto.customer.user.CustomerResponse customer = repository.userRepository.findCustomerById(id);
        if (customer == null) {
            throw new WsException(WsCode.MUST_LOGIN);
        }
        return customer;

    }


}
