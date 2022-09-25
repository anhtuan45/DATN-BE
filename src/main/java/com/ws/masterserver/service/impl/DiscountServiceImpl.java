package com.ws.masterserver.service.impl;

import com.ws.masterserver.dto.admin.discount.search.DiscountResponse;
import com.ws.masterserver.dto.customer.discount.DiscountReq;
import com.ws.masterserver.dto.customer.discount.DiscountRes;
import com.ws.masterserver.dto.customer.discount.MyDiscountResponse;
import com.ws.masterserver.entity.DiscountEntity;
import com.ws.masterserver.service.DiscountService;
import com.ws.masterserver.utils.base.WsRepository;
import com.ws.masterserver.utils.base.rest.CurrentUser;
import com.ws.masterserver.utils.base.rest.PageData;
import com.ws.masterserver.utils.base.rest.ResData;
import com.ws.masterserver.utils.common.*;
import com.ws.masterserver.utils.constants.WsCode;
import com.ws.masterserver.utils.constants.enums.DiscountStatusEnums;
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
public class DiscountServiceImpl implements DiscountService {

    private final WsRepository repository;

    @Override
    public Object getListMyDiscount(CurrentUser currentUser, DiscountReq payload) {
        log.info("DiscountServiceImpl searchMyDiscount() with payload: {}", JsonUtils.toJson(payload));
        AuthValidator.checkCustomerAndStaff(currentUser);
        Pageable pageable = PageableUtils.getPageable(payload.getPageReq());

        Page<DiscountEntity> discountEntityPage = repository.discountRepository
                .searchMyDiscount(currentUser.getId(), pageable);
        if (discountEntityPage.isEmpty()) {
            return PageData.setEmpty(payload.getPageReq());
        }
        return PageData.setResult(discountEntityPage.getContent()
                        .stream()
                        .map(o -> {
                            Date startDate = o.getStartDate();
                            Date endDate = o.getEndDate();
                            return MyDiscountResponse.builder()
                                    .discountId(o.getId())
                                    .code(o.getCode())
                                    .des(DiscountUtils.getDes(o, repository))
                                    .startDate(startDate)
                                    .endDate(endDate)
                                    .build();
                        })
                        .collect(Collectors.toList()),
                discountEntityPage.getNumber(),
                discountEntityPage.getSize(),
                discountEntityPage.getTotalElements());
    }
}
