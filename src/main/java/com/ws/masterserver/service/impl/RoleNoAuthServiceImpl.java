package com.ws.masterserver.service.impl;

import com.ws.masterserver.dto.no_auth.RoleDto;
import com.ws.masterserver.service.RoleNoAuthService;
import com.ws.masterserver.utils.constants.enums.RoleEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoleNoAuthServiceImpl implements RoleNoAuthService {
    @Override
    public Object modify() {
        return getRoles(new ArrayList<>());
    }

    @Override
    public Object search() {
        List<RoleDto> list = new ArrayList<>();
        list.add(RoleDto.builder()
                .roleCode("")
                .roleName("Tất cả")
                .build());
        return getRoles(list);
    }

    private List<RoleDto> getRoles(List<RoleDto> list) {
        for (RoleEnum role : RoleEnum.values()) {
            if (RoleEnum.ROLE_GUEST.equals(role)) {
                continue;
            }
            list.add(RoleDto.builder()
                    .roleCode(role.name())
                    .roleName(role.getName())
                    .build());
        }
        return list;
    }
}
