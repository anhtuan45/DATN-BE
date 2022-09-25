package com.ws.masterserver.service.impl;

import com.ws.masterserver.dto.admin.customer.search.CustomerReq;
import com.ws.masterserver.dto.admin.customer.search.CustomerRes;
import com.ws.masterserver.entity.UserEntity;
import com.ws.masterserver.service.AdminCustomerDetailService;
import com.ws.masterserver.utils.base.WsRepository;
import com.ws.masterserver.utils.base.rest.CurrentUser;
import com.ws.masterserver.utils.base.rest.PageData;
import com.ws.masterserver.utils.common.DateUtils;
import com.ws.masterserver.utils.common.PageableUtils;
import com.ws.masterserver.utils.common.StringUtils;
import com.ws.masterserver.utils.constants.enums.RoleEnum;
import com.ws.masterserver.utils.validator.auth.AuthValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminCustomerDetailServiceImpl implements AdminCustomerDetailService {
    private final WsRepository repository;

    @Override
    public Object search(CurrentUser currentUser, CustomerReq payload) {
        AuthValidator.checkAdmin(currentUser);
        String textSearch;
        if (StringUtils.isNullOrEmpty(payload.getTextSearch())) {
            payload.setTextSearch("");
        }
        textSearch = "%" + payload.getTextSearch().trim().toUpperCase(Locale.ROOT) + "%";

        Pageable pageable = PageableUtils.getPageable(payload.getPageReq());
        Page<UserEntity> userEntityPage = repository.userRepository.searchCustomer(RoleEnum.ROLE_CUSTOMER, payload.getActive(), textSearch, pageable);

        return PageData.setResult(userEntityPage.getContent().stream().map(o -> {
                    java.util.Date recentOrder = repository.orderRepository.findRecentOrderByCustomerId(o.getId());
                    return CustomerRes.builder()
                            .id(o.getId())
                            .firstName(o.getFirstName())
                            .lastName(o.getLastName())
                            .combinationName(o.getFirstName() + " " + o.getLastName())
                            .dob(o.getDob())
                            .dobValue(o.getDob() == null ? null : DateUtils.toStr(o.getDob(), DateUtils.F_DDMMYYYY))
                            .age(o.getDob() == null ? null : Period.between(LocalDate.now(), DateUtils.dateToLocalDate(o.getDob())).getYears())
                            .gender(o.getGender())
                            .email(o.getEmail())
                            .phone(o.getPhone())
                            .active(o.getActive())
                            .createdDate(o.getCreatedDate())
                            .createdDateValue(o.getCreatedDate() == null ? null : DateUtils.toStr(o.getCreatedDate(), DateUtils.F_DDMMYYYY))
                            .orderNumber(repository.orderRepository.countByUserId(o.getId()))
                            .recentOrder(recentOrder)
                            .recentOrderValue(recentOrder == null ? null : DateUtils.toStr(recentOrder, DateUtils.F_DDMMYYYY))
                            .build();
                }).collect(Collectors.toList()),
                userEntityPage.getNumber(),
                userEntityPage.getSize(),
                userEntityPage.getTotalElements());
    }
}
