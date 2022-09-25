package com.ws.masterserver.service.impl;

import com.ws.masterserver.dto.customer.order.OrderDetailRes;
import com.ws.masterserver.dto.customer.order.OrderDetailResponse;
import com.ws.masterserver.dto.customer.order.ProductInOrderDetail;
import com.ws.masterserver.service.OrderDetailService;
import com.ws.masterserver.utils.base.WsRepository;
import com.ws.masterserver.utils.base.rest.CurrentUser;
import com.ws.masterserver.utils.base.rest.ResData;
import com.ws.masterserver.utils.common.DateUtils;
import com.ws.masterserver.utils.common.MoneyUtils;
import com.ws.masterserver.utils.constants.WsCode;
import com.ws.masterserver.utils.constants.enums.PaymentEnums;
import com.ws.masterserver.utils.constants.enums.StatusEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderDetailServiceImpl implements OrderDetailService {
    private final WsRepository repository;

    @Override
    public Object getOrderDetail(CurrentUser currentUser,String orderId) {

        if (!repository.orderRepository.existsById(orderId)) {
            return new ResData<>(true);
        }

        OrderDetailRes orderDetail = repository.orderDetailRepository.getOrderDetail(orderId);
        PaymentEnums payment = PaymentEnums.from(orderDetail.getPaymentMethod());

        OrderDetailResponse orderDetailResponse = OrderDetailResponse.builder()
                .nameOfRecipient(orderDetail.getNameOfRecipient())
                .phoneNumber(orderDetail.getPhoneNumber())
                .totalPrice(MoneyUtils.formatV2(orderDetail.getTotalPrice()))
                .shipPrice(MoneyUtils.formatV2(orderDetail.getShipPrice()))
                .note(orderDetail.getNote())
                .payed(orderDetail.isPayed())
                .shipAddress(orderDetail.getShipAddress())
                .paymentMethod(payment == null ? null : payment.getName())
                .orderCode(orderDetail.getOrderCode())
                .createDate(DateUtils.parseDateToStr(DateUtils.F_DDMMYYYYHHMMSS,orderDetail.getCreateDate()))
                .statusOrder(StatusEnum.from(orderDetail.getStatusOrder()) == null ? null : StatusEnum.from(orderDetail.getStatusOrder()).getName())
                .status(orderDetail.getStatusOrder())
                .totalDiscount(MoneyUtils.formatV2(orderDetail.getShipPriceDiscount()+orderDetail.getShopPriceDiscount()))
                .build();

        List<ProductInOrderDetail> productOrder = repository.orderDetailRepository.getProductList(orderId,currentUser.getId());

        orderDetailResponse.setProduct(productOrder);

        return new ResData<>(orderDetailResponse, WsCode.OK);
    }
}
