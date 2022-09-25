package com.ws.masterserver.service.impl;

import com.ws.masterserver.dto.admin.order.change_status.ChangeStatusDto;
import com.ws.masterserver.entity.NotificationEntity;
import com.ws.masterserver.entity.OrderEntity;
import com.ws.masterserver.entity.OrderStatusEntity;
import com.ws.masterserver.entity.UserEntity;
import com.ws.masterserver.service.AdminOrderService;
import com.ws.masterserver.utils.base.WsException;
import com.ws.masterserver.utils.base.WsRepository;
import com.ws.masterserver.utils.base.rest.CurrentUser;
import com.ws.masterserver.utils.common.StringUtils;
import com.ws.masterserver.utils.common.UidUtils;
import com.ws.masterserver.utils.constants.WsCode;
import com.ws.masterserver.utils.constants.enums.NotificationTypeEnum;
import com.ws.masterserver.utils.constants.enums.ObjectTypeEnum;
import com.ws.masterserver.utils.constants.enums.StatusEnum;
import com.ws.masterserver.utils.validator.auth.AuthValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Locale;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminOrderServiceImpl implements AdminOrderService {
    private final WsRepository repository;

    @Override
    @Transactional
    public Object changeStatus(CurrentUser currentUser, ChangeStatusDto dto) {
        AuthValidator.checkAdminAndStaff(currentUser);
        if (StringUtils.isNullOrEmpty(dto.getStatus())) {
            throw new WsException(WsCode.ERROR_NOT_FOUND);
        }
        OrderEntity order = repository.orderRepository.findByIdLock(dto.getId());
        StatusEnum status = StatusEnum.from(dto.getStatus().toUpperCase(Locale.ROOT));
        StatusEnum nowStatus = StatusEnum.from(order.getStatus());

        if (status == null || order == null) {
            throw new WsException(WsCode.ERROR_NOT_FOUND);
        }
        if (StatusEnum.CANCELED.equals(nowStatus)) {
            throw new WsException(WsCode.ORDER_HAS_CANCELED_BY_CUSTOMER);
        }

        if(StatusEnum.REJECTED.equals(nowStatus)){
            if(status.equals(StatusEnum.EXCHANGE) || status.equals(StatusEnum.REFUND) || status.equals(StatusEnum.RECEIVED)){
                throw new WsException(WsCode.ORDER_HAS_REJECTED_BY_ADMIN);
            }
        }

        UserEntity user = repository.userRepository.findById(order.getUserId())
                .orElseThrow(() -> new WsException(WsCode.USER_NOT_FOUND));

        if(status.equals(StatusEnum.RECEIVED)){
            order.setPayed(Boolean.TRUE);

        }

        order.setStatus(status.name());
        order.setUpdatedBy(currentUser.getId());
        order.setUpdatedDate(new Date());
        repository.orderRepository.save(order);

        OrderStatusEntity orderStatus = OrderStatusEntity.builder()
                .id(UidUtils.generateUid())
                .status(status.name())
                .createdBy(currentUser.getId())
                .createdDate(new Date())
                .orderId(dto.getId())
                .note(dto.getNote())
                .build();
        repository.orderStatusRepository.save(orderStatus);


        NotificationEntity notification = NotificationEntity.builder()
                .id(UidUtils.generateUid())
                .createdDate(new Date())
                .isRead(Boolean.FALSE)
                .type(NotificationTypeEnum.NORMAL.name())
                .objectType(ObjectTypeEnum.ORDER.name())
//                .type(NotificationTypeEnum.NORMAL)
//                .objectType(ObjectTypeEnum.ORDER)
                .objectTypeId(order.getId())
                .userId(order.getUserId())
                .content("Đơn hàng " + order.getCode() + " của bạn " + status.getName())
                .build();

        repository.notificationRepository.save(notification);

        return order.getId();
    }


}
