package com.ws.masterserver.service.impl;

import com.ws.masterserver.dto.admin.order.detail.DetailRes;
import com.ws.masterserver.dto.admin.order.detail.ItemDto;
import com.ws.masterserver.dto.admin.order.detail.StatusDto;
import com.ws.masterserver.dto.admin.order.search.OrderReq;
import com.ws.masterserver.entity.*;
import com.ws.masterserver.service.AdminOrderDetailService;
import com.ws.masterserver.utils.base.WsException;
import com.ws.masterserver.utils.base.WsRepository;
import com.ws.masterserver.utils.base.rest.CurrentUser;
import com.ws.masterserver.utils.common.*;
import com.ws.masterserver.utils.constants.WsCode;
import com.ws.masterserver.utils.constants.enums.PaymentEnums;
import com.ws.masterserver.utils.constants.enums.RoleEnum;
import com.ws.masterserver.utils.constants.enums.StatusEnum;
import com.ws.masterserver.utils.validator.auth.AuthValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminOrderDetailServiceImpl implements AdminOrderDetailService {
    private final WsRepository repository;

    @Override
    public Object search(CurrentUser currentUser, OrderReq req) {
        AuthValidator.checkAdminAndStaff(currentUser);
        return repository.adminOrderCustomRepository.search(currentUser, req);
    }

    @Override
    public Object detail(CurrentUser currentUser, String id) {
        AuthValidator.checkAdminAndStaff(currentUser);
        OrderEntity order = repository.orderRepository.findById(id).orElseThrow(() -> new WsException(WsCode.ERROR_NOT_FOUND));
        DiscountEntity discount = null;
        if (!StringUtils.isNullOrEmpty(order.getDiscountId())) {
            discount = repository.discountRepository.findById(order.getDiscountId()).orElse(null);
        }
        AddressEntity address = repository.addressRepository.findByIdAndUserId(order.getAddressId(), order.getUserId());
        UserEntity customer = repository.userRepository.findByIdAndRole(order.getUserId(), RoleEnum.ROLE_CUSTOMER);
        PaymentEnums paymentEnums = PaymentEnums.from(order.getPayment());
        DetailRes.DetailResBuilder resBuilder = DetailRes.builder();
        AtomicLong totalPriceItems = new AtomicLong(0L);
        AtomicLong totalQtyItems = new AtomicLong(0L);
        AtomicLong totalDiscountItems = new AtomicLong(0L);
        AtomicLong totalItems = new AtomicLong(0L);
        StatusEnum statusEnum = StatusEnum.from(order.getStatus());

        long shipTotal = order.getShipPrice() - Optional.ofNullable(order.getShipPriceDiscount()).orElse(0L);
        resBuilder
                .id(order.getId())
                .code(order.getCode())
                .note(order.getNote())
                .payed(order.getPayed())
                .shipMethod(order.getShipMethod())
                .shipPrice(order.getShipPrice())
                .shipPriceFmt(MoneyUtils.formatV2(order.getShipPrice()))
                .shipDiscount(order.getShipPriceDiscount())
                .shipDiscountFmt(MoneyUtils.formatV2(order.getShipPriceDiscount()))
                .shipTotal(shipTotal)
                .shipTotalFmt(MoneyUtils.formatV2(shipTotal))
        ;

        if (statusEnum != null) {
            resBuilder.status(statusEnum.name())
                    .statusName(statusEnum.getName());
        }

        if (paymentEnums != null) {
            resBuilder
                    .paymentCode(paymentEnums.getCode())
                    .paymentName(paymentEnums.getName());
        }

        if (customer != null) {
            resBuilder
                    .customerId(customer.getId())
                    .customerName(customer.getFirstName() + " " + customer.getLastName())
            ;
        }

        if (address != null) {
            resBuilder
                    .addressCombination(AddressUtils.getCombination(address))
                    .phone(StringUtils.isNullOrEmpty(address.getPhoneNumber()) ? null : StringUtils.isNullOrEmpty(customer.getPhone()) ? null : customer.getPhone())
                    .nameOfRecipient(address.getNameOfRecipient())
            ;
        }

        if (discount != null) {
            resBuilder
                    .discountId(discount.getId())
                    .discountCode(discount.getCode())
                    .discountDes(DiscountUtils.getDes(discount, repository));
        }

        List<ItemDto> items = repository.orderDetailRepository.getOrderDetailItemByOrderId(id).stream().map(obj -> {
            Long itemPrice = obj.getPrice();
            Long itemDiscount = obj.getDiscount();
            Long itemTotal = obj.getTotal();

            totalPriceItems.addAndGet(itemPrice);
            obj.setPriceFmt(MoneyUtils.formatV2(itemPrice));

            totalDiscountItems.addAndGet(itemDiscount);
            obj.setDiscountFmt(MoneyUtils.formatV2(itemDiscount));

            totalQtyItems.addAndGet(obj.getQty());
            totalItems.addAndGet(itemTotal);
            obj.setTotalFmt(MoneyUtils.formatV2(itemTotal));

            return obj;
        }).collect(Collectors.toList());

        resBuilder
                .items(items)
                .totalPriceItems(totalPriceItems.get())
                .totalPriceItemsFmt(MoneyUtils.formatV2(totalPriceItems.get()))
                .totalDiscountItems(totalDiscountItems.get())
                .totalDiscountItemsFmt(MoneyUtils.formatV2(totalDiscountItems.get()))
                .totalQtyItems(totalQtyItems.get())
                .totalItems(totalItems.get())
                .totalItemsFmt(MoneyUtils.formatV2(totalItems.get()))
                .total(order.getTotal())
                .totalFmt(MoneyUtils.formatV2(order.getTotal()))
        ;

        List<StatusDto> orderStatusList = repository.orderStatusRepository.findHistory(id);
        if (!orderStatusList.isEmpty()) {
            resBuilder.history(OrderUtils.getHistory(orderStatusList));
        }
        return resBuilder.build();
    }

}
