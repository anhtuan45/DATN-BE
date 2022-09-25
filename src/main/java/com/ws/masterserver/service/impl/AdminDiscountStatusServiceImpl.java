package com.ws.masterserver.service.impl;

import com.ws.masterserver.service.AdminDiscountStatusService;
import com.ws.masterserver.utils.base.WsException;
import com.ws.masterserver.utils.base.WsRepository;
import com.ws.masterserver.utils.base.rest.CurrentUser;
import com.ws.masterserver.utils.base.rest.ResData;
import com.ws.masterserver.utils.constants.WsCode;
import com.ws.masterserver.utils.validator.auth.AuthValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminDiscountStatusServiceImpl implements AdminDiscountStatusService {

    private final WsRepository repository;

    @Override
    public Object delete(CurrentUser currentUser, String id) {
        AuthValidator.checkAdmin(currentUser);
        com.ws.masterserver.entity.DiscountEntity discount = repository.discountRepository.findByIdAndDeleted(id, false);
        if (null == discount) {
            throw new WsException(WsCode.ERROR_NOT_FOUND);
        }
        if (repository.orderRepository.checkDiscountHasUsed(id)) {
            throw new WsException(WsCode.DISCOUNT_HAS_USED_CAN_NOT_DELETE);
        }
        discount.setDeleted(true);
        repository.discountRepository.save(discount);
        return ResData.ok(discount.getId());
    }
}
