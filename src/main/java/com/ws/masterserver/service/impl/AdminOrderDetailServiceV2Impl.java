package com.ws.masterserver.service.impl;

import com.ws.masterserver.dto.admin.order.search.OrderReq;
import com.ws.masterserver.dto.admin.order.search.OrderRes;
import com.ws.masterserver.entity.AddressEntity;
import com.ws.masterserver.entity.OrderEntity;
import com.ws.masterserver.entity.UserEntity;
import com.ws.masterserver.service.AdminOrderDetailServiceV2;
import com.ws.masterserver.utils.base.WsRepository;
import com.ws.masterserver.utils.base.rest.CurrentUser;
import com.ws.masterserver.utils.base.rest.PageData;
import com.ws.masterserver.utils.common.*;
import com.ws.masterserver.utils.constants.enums.PaymentEnums;
import com.ws.masterserver.utils.constants.enums.StatusEnum;
import com.ws.masterserver.utils.validator.auth.AuthValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminOrderDetailServiceV2Impl implements AdminOrderDetailServiceV2 {
    private final WsRepository repository;

    @Override
    public Object search(CurrentUser currentUser, OrderReq payload) {
        AuthValidator.checkAdminAndStaff(currentUser);
        StatusEnum status = StatusEnum.from(payload.getStatus());
        String statusStr = null;
        if (status != null) statusStr = status.name();

        String textSearch = "";
        if (!StringUtils.isNullOrEmpty(payload.getTextSearch())) {
            textSearch = payload.getTextSearch().trim().toUpperCase();
        }

        Pageable pageable = PageableUtils.getPageable(payload.getPageReq());
        Page<OrderEntity> orderEntityPage =
                repository.orderRepository
                        .searchV2(statusStr,
                                payload.getProvinceId(),
                                payload.getDistrictId(),
                                payload.getWardCode(),
                                "%" + textSearch + "%",
                                pageable);
        return PageData.setResult(orderEntityPage.getContent().stream().map(this::buildOrderRes).collect(Collectors.toList()),
                orderEntityPage.getNumber(),
                orderEntityPage.getSize(),
                orderEntityPage.getTotalElements());
    }

    private OrderRes buildOrderRes(OrderEntity o) {
        AddressEntity addressEntity = repository.addressRepository.findById(o.getAddressId()).orElse(new AddressEntity());
        UserEntity customer = repository.userRepository.findById(o.getUserId()).orElse(new UserEntity());
        UserEntity lastModifiedUser = repository.userRepository.findByIdSkip(o.getUpdatedBy());
        String combinationAddress = addressEntity.getExact() + ", " + addressEntity.getWardName() + " " + addressEntity.getDistrictName() + " " + addressEntity.getProvinceName();
        PaymentEnums payment = PaymentEnums.from(o.getPayment());
        Date updatedDate = o.getUpdatedDate();
        StatusEnum statusEnum = StatusEnum.from(o.getStatus());
        Date createdDate = o.getCreatedDate();
        return OrderRes.builder()
                .id(o.getId())
                .addressCombination(combinationAddress)
                .code(o.getCode())
                .createdDate(createdDate)
                .createdDateFmt(createdDate == null ? null : DateUtils.toStr(createdDate, DateUtils.F_DDMMYYYYHHMM))
                .updatedDate(updatedDate)
                .updatedDateFmt(null == updatedDate ? null : DateUtils.toStr(updatedDate, DateUtils.F_DDMMYYYYHHMM))
                .customerId(o.getUserId())
                .customerName(customer.getFirstName() + " " + customer.getLastName())
                .phone(customer.getPhone())
                .total(o.getTotal())
                .totalFmt(MoneyUtils.formatV2(o.getTotal()))
                .note(o.getNote())
                .options(OrderUtils.getOptions4Admin(o.getStatus()))
                .statusCode(null == statusEnum ? null : statusEnum.name())
                .statusName(null == statusEnum ? null : statusEnum.getName())
                .statusCombination(OrderUtils.getStatusCombination(statusEnum, updatedDate, lastModifiedUser))
                .payment(payment == null ? null : payment.name())
                .paymentName(payment == null ? null : payment.getName())
                .payed(o.getPayed())
                .build();
    }
}
