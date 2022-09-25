package com.ws.masterserver.service.impl;

import com.ws.masterserver.dto.admin.discount.type.ShipTypeDto;
import com.ws.masterserver.dto.customer.cart.response.CartResponse;
import com.ws.masterserver.dto.customer.mail.OrderInfoMail;
import com.ws.masterserver.dto.customer.order.*;
import com.ws.masterserver.dto.customer.order.order_detail.ProductOrderDetail;
import com.ws.masterserver.entity.*;
import com.ws.masterserver.service.MailService;
import com.ws.masterserver.service.OrderService;
import com.ws.masterserver.utils.base.WsException;
import com.ws.masterserver.utils.base.WsRepository;
import com.ws.masterserver.utils.common.*;
import com.ws.masterserver.utils.constants.WsCode;
import com.ws.masterserver.utils.constants.WsConst;
import com.ws.masterserver.utils.constants.enums.*;
import com.ws.masterserver.utils.base.rest.CurrentUser;
import com.ws.masterserver.utils.base.rest.ResData;
import com.ws.masterserver.utils.base.rest.PageData;
import com.ws.masterserver.utils.validator.auth.AuthValidator;
import com.ws.masterserver.utils.validator.customer.order.CancelOrderValidator;
import com.ws.masterserver.utils.validator.customer.order.CheckoutValidator;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@SuppressWarnings("deprecation")
public class OrderServiceImpl implements OrderService {

    public static final String NAME = "name";
    public static final String EMAIL = "email";
    public static final String PHONE = "phone";
    public static final String ADDRESS = "address";
    public static final String NAME_OF_RECIPIENT = "nameOfRecipient";
    public static final String PHONE_OF_RECIPIENT = "phoneOfRecipient";
    public static final String PAYMENT_METHOD = "paymentMethod";
    public static final String SHIP_METHOD = "shipMethod";
    public static final String ORDER_CODE = "orderCode";
    public static final String CREATE_DATE = "createDate";
    public static final String SHIP_PRICE = "shipPrice";
    public static final String TOTAL = "total";
    public static final String NOTE = "note";
    public static final String PRODUCT = "product";
    public static final String SHIP_PRICE_DISCOUNT = "shipPriceDiscount";
    public static final String TOTAL_DISCOUNT = "totalDiscount";
    public static final String SHOP_PRICE_DISCOUNT = "shopPriceDiscount";
    private final WsRepository repository;
    private final MailService mailService;
    private final Configuration configuration;

    @Value("${spring.mail.username}")
    private String email;

    private static final String ORDER_INFO_TEMPLATE_NAME = "order_info.ftl";
    private static final String FROM = "WOMAN SHIRT<%s>";
    private static final String SUBJECT = "Xác nhận đơn hàng ";

    @Override
    @Transactional
    public Object checkout(CurrentUser currentUser, OrderReqq payload) {
        log.info("checkout payload: {}", JsonUtils.toJson(payload));
        AuthValidator.checkCustomerAndStaff(currentUser);
        CheckoutValidator.validCheckout(payload);

        AddressEntity address = repository.addressRepository.findById(payload.getAddressId()).orElseThrow(() -> {
            throw new WsException(WsCode.ADDRESS_NOT_FOUND);
        });

        List<CartResponse> cart = repository.cartRepository.getListCart(currentUser.getId());

        if (cart.isEmpty()) {
            throw new WsException(WsCode.CART_IS_EMPTY);
        }

        String orderCode = "#".concat(String.valueOf(repository.orderRepository.getNextCodeOrder()));
        PaymentEnums payment = PaymentEnums.from(payload.getPaymentMethod());

        if (payment == null) {
            throw new WsException(WsCode.PAYMENT_NOT_FOUND);
        }



        OrderEntity order;

        if(payload.getDiscountCode() == null || payload.getDiscountCode().equalsIgnoreCase("")){
             order = OrderEntity.builder()
                    .id(UidUtils.generateUid())
                    .addressId(address.getId())
                    .userId(currentUser.getId())
                    .note(payload.getNote())
                    .payment(payload.getPaymentMethod())
                    .shipPrice(Long.valueOf(payload.getShipPrice()))
                    .payment(payment == null ? null : payment.name())
                    .shipPrice(Long.valueOf(payload.getShipPrice()))
                    .shipMethod(payload.getShipMethod())
                    .total(payload.getTotal())
                    .code(orderCode)
                    .payed(Boolean.FALSE)
                    .status(StatusEnum.PENDING.name())
                    .createdDate(new Date())
                    .updatedDate(new Date())
                    .createdBy(currentUser.getCombinationName())
                    .updatedBy(currentUser.getCombinationName())
                    .shopPrice(payload.getShopPrice())
                     .shipPriceDiscount(0L)
                     .shopPriceDiscount(0L)
                    .build();

        }else{
            DiscountEntity dc = repository.discountRepository.findByCode(payload.getDiscountCode());
             order = OrderEntity.builder()
                    .id(UidUtils.generateUid())
                    .addressId(address.getId())
                    .userId(currentUser.getId())
                    .note(payload.getNote())
                    .payment(payload.getPaymentMethod())
                    .shipPrice(Long.valueOf(payload.getShipPrice()))
                    .payment(payment == null ? null : payment.name())
                    .shipPrice(Long.valueOf(payload.getShipPrice()))
                    .shipMethod(payload.getShipMethod())
                    .total(payload.getTotal())
                    .code(orderCode)
                    .payed(Boolean.FALSE)
                    .status(StatusEnum.PENDING.name())
                    .createdDate(new Date())
                    .updatedDate(new Date())
                    .createdBy(currentUser.getCombinationName())
                    .updatedBy(currentUser.getCombinationName())
                    .shipPriceDiscount(payload.getShipPriceDiscount())
                    .shopPriceDiscount(payload.getShopPriceDiscount())
                    .shopPrice(payload.getShopPrice())
                    .discountId(dc.getId())
                    .build();
        }



        log.info("----- OrderServiceImpl create before save: {}", JsonUtils.toJson(order));
        repository.orderRepository.save(order);

        log.info("----- OrderServiceImpl create before save: {}", JsonUtils.toJson(order));


        repository.orderStatusRepository.save(OrderStatusEntity.builder()
                .id(UUID.randomUUID().toString())
                .orderId(order.getId())
                .status(StatusEnum.PENDING.name())
                .createdDate(new Date())
                .createdBy(currentUser.getId())
                .build());

        //update số lượng sản phảm sau khi đã update
        cart.stream().map(item -> {
            ProductOptionEntity productOption = repository.productOptionRepository.findById(item.getProductOptionId()).orElseThrow(() -> {
                throw new WsException(WsCode.PRODUCT_OPTION_NOT_FOUND);
            });

            productOption.setQty(productOption.getQty() - item.getQuantity());

            return repository.productOptionRepository.save(productOption);

        }).collect(Collectors.toList());



        if(payload.getDiscountCode() == null || payload.getDiscountCode().equalsIgnoreCase("")){
            //save order to orderDetail
            cart.stream().map(item -> {
                OrderDetailEntity orderDetail = OrderDetailEntity.builder()
                        .id(UidUtils.generateUid())
                        .orderId(order.getId())
                        .productOptionId(item.getProductOptionId())
                        .price(item.getPrice())
                        .qty(item.getQuantity())
                        .build();

                return repository.orderDetailRepository.save(orderDetail);

            }).collect(Collectors.toList());
        }else {
            payload.getCart().stream().map(item -> {
                OrderDetailEntity orderDetail = OrderDetailEntity.builder()
                        .id(UidUtils.generateUid())
                        .orderId(order.getId())
                        .productOptionId(item.getProductOptionId())
                        .price(item.getPrice())
                        .qty(item.getQuantity())
                        .discount(item.getDiscount())
                        .build();

                return repository.orderDetailRepository.save(orderDetail);

            }).collect(Collectors.toList());
        }

        repository.cartRepository.clearCart(currentUser.getId());

        NotificationEntity notification = NotificationUtils.buildNotification(
                String.format(NotificationUtils.CUSTOMER_CHECKOUT_TEMP, currentUser.getCombinationName()),
                ObjectTypeEnum.ORDER.name(),
                order.getId(),
                NotificationTypeEnum.NORMAL.name()
        );

//        NotificationEntity notification = NotificationEntity.builder()
//                .id(UidUtils.generateUid())
//                .createdDate(new Date())
//                .isRead(Boolean.FALSE)
//                .type(NotificationTypeEnum.NORMAL)
//                .objectType(ObjectTypeEnum.ORDER)
//                .objectTypeId(order.getId())
//                .content("Khách hàng " + currentUser.getCombinationName() + " vừa đặt hàng thành công. Vui lòng xử lý đơn hàng")
//                .build();

        repository.notificationRepository.save(notification);

        UserEntity user = repository.userRepository.findById(currentUser.getId()).orElseThrow(() -> {
            throw new WsException(WsCode.USER_NOT_FOUND);
        });

        //sendMail4OrderInfo
        this.sendMail4OrderInfo(order, user, address, cart, user.getEmail());

        //service

        return new ResData<>(order.getId(), WsCode.CREATED);

    }

    private Long getShipPrice(DiscountEntity discount, OrderRequest req) {
        Long shipPriceReq = Long.valueOf(req.getShipPrice());
        if (discount == null) {
            return shipPriceReq;
        }
        DiscountTypeEnums discountType = DiscountTypeEnums.from(discount.getType());
        if (DiscountTypeEnums.SHIP.equals(discountType)) {
            ShipTypeDto shipDto = JsonUtils.fromJson(discount.getTypeValue(), ShipTypeDto.class);
            if (!StringUtils.isNullOrEmpty(shipDto.getShipValueLimitAmount())) {
                shipPriceReq -= shipPriceReq * Long.valueOf(shipDto.getShipValueLimitAmount());
            }
        }

        return Math.max(shipPriceReq, 0L);
    }

    @Override
    @Transactional
    public ResData<String> cancelOrder(CurrentUser currentUser, CancelOrder payload) {
        AuthValidator.checkCustomerAndStaff(currentUser);
        log.info("cancelOrder() payload: {}", JsonUtils.toJson(payload));
        CancelOrderValidator.validCancelOrder(payload);
        OrderEntity order = repository.orderRepository.findByIdAndUserIdLock(payload.getOrderId(), currentUser.getId());
        log.info("cancelOrder() order: {}", JsonUtils.toJson(order));
        if (order == null) {
            throw new WsException(WsCode.ERROR_NOT_FOUND);
        }

        StatusEnum statusNow = StatusEnum.from(order.getStatus());
        log.info("cancelOrder() statusNow: {}", JsonUtils.toJson(statusNow));
        if (statusNow == null || !StatusEnum.PENDING.equals(statusNow)) {
            throw new WsException(WsCode.FORBIDDEN);
        }
        List<ProductOrderDetail> orderDetail = repository.orderDetailRepository.getProductOrder(order.getId());
        log.info("cancelOrder() orderDetail: {}", JsonUtils.toJson(orderDetail));

        orderDetail.stream().forEach(item -> {
            ProductOptionEntity productOption = repository.productOptionRepository.findById(item.getProductOptionId()).orElse(null);
            if (productOption == null) {
                throw new WsException(WsCode.ERROR_NOT_FOUND);
            }
            productOption.setQty(productOption.getQty() + item.getQuantity());
            repository.productOptionRepository.save(productOption);
        });

        OrderStatusEntity orderStatus = OrderStatusEntity.builder()
                .id(UidUtils.generateUid())
                .status(StatusEnum.CANCELED.name())
                .note(payload.getNote())
                .createdDate(new Date())
                .orderId(order.getId())
                .createdBy(order.getUserId())
                .build();
        log.info("cancelOrder() orderStatus before: {}", JsonUtils.toJson(orderStatus));
        repository.orderStatusRepository.save(orderStatus);
        log.info("cancelOrder() orderStatus after: {}", JsonUtils.toJson(orderStatus));

        order.setStatus(StatusEnum.CANCELED.name());
        order.setUpdatedBy(currentUser.getId());
        order.setUpdatedDate(new Date());
        log.info("cancelOrder() order before: {}", JsonUtils.toJson(order));
        repository.orderRepository.save(order);
        log.info("cancelOrder() order after: {}", JsonUtils.toJson(order));

        NotificationEntity notification = NotificationUtils.buildNotification(
                String.format(NotificationUtils.CUSTOMER_CANCEL_ORDER_TEMP, currentUser.getCombinationName(), order.getCode()),
                ObjectTypeEnum.ORDER.name(),
                order.getId(),
                NotificationTypeEnum.WARNING.name()
        );
        repository.notificationRepository.save(notification);

        return ResData.ok(order.getId(), "Hủy đơn hàng thành công !");
    }

    @Override
    public Object getMyOrders(CurrentUser currentUser) {
        List<OrderEntity> orders = repository.orderRepository.getMyOrder(currentUser.getId());

        ListOrderRes res = new ListOrderRes();

        res.setOrderRes(
                orders.stream().map(item -> {
                    AddressEntity address = repository.addressRepository.findById(item.getAddressId()).orElse(null);
                    if (address == null) {
                        throw new WsException(WsCode.ADDRESS_NOT_FOUND);
                    }

                    OrderResponse orderRes = OrderResponse.builder()
                            .orderCode(item.getCode())
                            .orderId(item.getId())
                            .status(StatusEnum.from(item.getStatus()))
                            .createDate(DateUtils.parseDateToStr(DateUtils.F_DDMMYYYYHHMMSS, item.getCreatedDate()))
                            .totalPrice(MoneyUtils.formatV2(item.getTotal()))
                            .payed(item.getPayed())
                            .address(address.getCombination())
                            .statusValue(StatusEnum.from(item.getStatus()).getName())
                            .build();

                    return orderRes;

                }).collect(Collectors.toList())
        );

        return new ResData<>(res, WsCode.OK);
    }

    @Override
    public Object search(CurrentUser currentUser, OrderSearch req) {
        log.info("----- My order search ----- ");
        AuthValidator.checkCustomerAndStaff(currentUser);
        org.springframework.data.domain.Pageable pageable = PageableUtils.getPageable(req.getPageReq());
        if (StringUtils.isNullOrEmpty(req.getTextSearch())) {
            req.setTextSearch("");
        }

        req.setTextSearch("%" + req.getTextSearch()
                .toUpperCase(Locale.ROOT)
                .trim() + "%");
        StatusEnum status = StatusEnum.from(req.getStatus());
        String statusStr = status == null ? null : status.name();
        Page<OrderEntity> orderPage = repository.orderRepository.search(req.getTextSearch(), currentUser.getId(), statusStr, pageable);

        if (orderPage.isEmpty()) {
            return PageData.setEmpty(req.getPageReq());
        }

        return PageData.setResult(
                orderPage.getContent()
                        .stream()
                        .map(o -> {
                            AddressEntity address = repository.addressRepository.findById(o.getAddressId()).orElseThrow(() -> new WsException(WsCode.ADDRESS_NOT_FOUND));

                            OrderResponse response = OrderResponse.builder()
                                    .orderId(o.getId())
                                    .orderCode(o.getCode())
                                    .status(StatusEnum.from(o.getStatus()))
                                    .statusValue(StatusEnum.from(o.getStatus()).getName())
                                    .address(address.getCombination())
                                    .createDate(DateUtils.toStr(o.getCreatedDate(), DateUtils.F_DDMMYYYYHHMM))
                                    .totalPrice(MoneyUtils.formatV2(o.getTotal()))
                                    .payed(o.getPayed())
                                    .build();

                            return response;
                        }).collect(Collectors.toList()),
                orderPage.getNumber(),
                orderPage.getSize(),
                orderPage.getTotalElements()
        );
    }

    @Override
    public Object countMyOrders(CurrentUser currentUser) {
        AuthValidator.checkCustomerAndStaff(currentUser);
        Integer numberOrders = repository.orderRepository.countOrder(currentUser.getId());

        return ResData.ok(numberOrders, "Thanh` co^ng !!");
    }

    @Override
    public void submitReceivedOrder(CurrentUser currentUser, SubmitOrderReceived req) {

        AuthValidator.checkCustomerAndStaff(currentUser);

        OrderEntity order = repository.orderRepository.findById(req.getOrderId())
                .orElseThrow(() -> new WsException(WsCode.ORDER_NOT_FOUND));

        order.setPayed(true);
        order.setStatus("RECEIVED");
        order.setUpdatedDate(new Date());

        repository.orderRepository.save(order);

        OrderStatusEntity orderStatus = OrderStatusEntity.builder()
                .id(UidUtils.generateUid())
                .status("RECEIVED")
                .createdBy(currentUser.getId())
                .createdDate(new Date())
                .orderId(order.getId())
                .note(req.getNote())
                .build();
        repository.orderStatusRepository.save(orderStatus);
    }

    @Override
    public Object checkout2(CurrentUser currentUser, OrderRequest payload) {
        log.info("checkout payload: {}", JsonUtils.toJson(payload));
        AuthValidator.checkCustomerAndStaff(currentUser);
        if(payload.getAddressId() == null || payload.getAddressId().equalsIgnoreCase("")){
            AddressEntity address = AddressEntity.builder()
                    .id(UidUtils.generateUid())
                    .userId(currentUser.getId())
                    .nameOfRecipient(payload.getNameOfRecipient().trim())
                    .phoneNumber(payload.getPhoneNumber().trim())
                    .exact(payload.getExact().trim())
                    .wardCode(payload.getWardCode().trim())
                    .wardName(payload.getWardName().trim())
                    .districtId(payload.getDistrictId().trim())
                    .districtName(payload.getDistrictName().trim())
                    .provinceId(payload.getProvinceId().trim())
                    .provinceName(payload.getProvinceName().trim())
                    .isDefault(Boolean.FALSE)
                    .active(Boolean.TRUE)
                    .combination(payload.getExact().trim().concat(", ").concat(payload.getWardName().trim()).concat(", ").concat(payload.getDistrictName().trim()).concat(", ").concat(payload.getProvinceName().trim()))
                    .createdBy(currentUser.getId())
                    .createdDate(new Date())
                    .build();

            repository.addressRepository.save(address);

            List<CartResponse> cart = repository.cartRepository.getListCart(currentUser.getId());

            if (cart.isEmpty()) {
                throw new WsException(WsCode.CART_IS_EMPTY);
            }

            String orderCode = "#".concat(String.valueOf(repository.orderRepository.getNextCodeOrder()));
            PaymentEnums payment = PaymentEnums.from(payload.getPaymentMethod());

            if (payment == null) {
                throw new WsException(WsCode.PAYMENT_NOT_FOUND);
            }



            OrderEntity order;

            if(payload.getDiscountCode() == null || payload.getDiscountCode().equalsIgnoreCase("")){
                order = OrderEntity.builder()
                        .id(UidUtils.generateUid())
                        .addressId(address.getId())
                        .userId(currentUser.getId())
                        .note(payload.getNote())
                        .payment(payload.getPaymentMethod())
                        .shipPrice(Long.valueOf(payload.getShipPrice()))
                        .payment(payment == null ? null : payment.name())
                        .shipPrice(Long.valueOf(payload.getShipPrice()))
                        .shipMethod(payload.getShipMethod())
                        .total(payload.getTotal())
                        .code(orderCode)
                        .payed(Boolean.FALSE)
                        .status(StatusEnum.PENDING.name())
                        .createdDate(new Date())
                        .updatedDate(new Date())
                        .createdBy(currentUser.getCombinationName())
                        .updatedBy(currentUser.getCombinationName())
                        .shopPrice(payload.getShopPrice())
                        .shipPriceDiscount(0L)
                        .shopPriceDiscount(0L)
                        .build();

            }else{
                DiscountEntity dc = repository.discountRepository.findByCode(payload.getDiscountCode());
                order = OrderEntity.builder()
                        .id(UidUtils.generateUid())
                        .addressId(address.getId())
                        .userId(currentUser.getId())
                        .note(payload.getNote())
                        .payment(payload.getPaymentMethod())
                        .shipPrice(Long.valueOf(payload.getShipPrice()))
                        .payment(payment == null ? null : payment.name())
                        .shipPrice(Long.valueOf(payload.getShipPrice()))
                        .shipMethod(payload.getShipMethod())
                        .total(payload.getTotal())
                        .code(orderCode)
                        .payed(Boolean.FALSE)
                        .status(StatusEnum.PENDING.name())
                        .createdDate(new Date())
                        .updatedDate(new Date())
                        .createdBy(currentUser.getCombinationName())
                        .updatedBy(currentUser.getCombinationName())
                        .shipPriceDiscount(payload.getShipPriceDiscount())
                        .shopPriceDiscount(payload.getShopPriceDiscount())
                        .shopPrice(payload.getShopPrice())
                        .discountId(dc.getId())
                        .build();
            }



            log.info("----- OrderServiceImpl create before save: {}", JsonUtils.toJson(order));
            repository.orderRepository.save(order);

            log.info("----- OrderServiceImpl create before save: {}", JsonUtils.toJson(order));


            repository.orderStatusRepository.save(OrderStatusEntity.builder()
                    .id(UUID.randomUUID().toString())
                    .orderId(order.getId())
                    .status(StatusEnum.PENDING.name())
                    .createdDate(new Date())
                    .createdBy(currentUser.getId())
                    .build());

            //update số lượng sản phảm sau khi đã update
            cart.stream().map(item -> {
                ProductOptionEntity productOption = repository.productOptionRepository.findById(item.getProductOptionId()).orElseThrow(() -> {
                    throw new WsException(WsCode.PRODUCT_OPTION_NOT_FOUND);
                });

                productOption.setQty(productOption.getQty() - item.getQuantity());

                return repository.productOptionRepository.save(productOption);

            }).collect(Collectors.toList());



            if(payload.getDiscountCode() == null || payload.getDiscountCode().equalsIgnoreCase("")){
                //save order to orderDetail
                cart.stream().map(item -> {
                    OrderDetailEntity orderDetail = OrderDetailEntity.builder()
                            .id(UidUtils.generateUid())
                            .orderId(order.getId())
                            .productOptionId(item.getProductOptionId())
                            .price(item.getPrice())
                            .qty(item.getQuantity())
                            .build();

                    return repository.orderDetailRepository.save(orderDetail);

                }).collect(Collectors.toList());
            }else {
                payload.getCart().stream().map(item -> {
                    OrderDetailEntity orderDetail = OrderDetailEntity.builder()
                            .id(UidUtils.generateUid())
                            .orderId(order.getId())
                            .productOptionId(item.getProductOptionId())
                            .price(item.getPrice())
                            .qty(item.getQuantity())
                            .discount(item.getDiscount())
                            .build();

                    return repository.orderDetailRepository.save(orderDetail);

                }).collect(Collectors.toList());
            }

            repository.cartRepository.clearCart(currentUser.getId());

            NotificationEntity notification = NotificationUtils.buildNotification(
                    String.format(NotificationUtils.CUSTOMER_CHECKOUT_TEMP, currentUser.getCombinationName()),
                    ObjectTypeEnum.ORDER.name(),
                    order.getId(),
                    NotificationTypeEnum.NORMAL.name()
            );

            repository.notificationRepository.save(notification);

            UserEntity user = repository.userRepository.findById(currentUser.getId()).orElseThrow(() -> {
                throw new WsException(WsCode.USER_NOT_FOUND);
            });

            //sendMail4OrderInfo
            this.sendMail4OrderInfo(order, user, address, cart, user.getEmail());

            //service

            return new ResData<>(order.getId(), WsCode.CREATED);
        }else{
            AddressEntity address = repository.addressRepository.findById(payload.getAddressId()).orElseThrow(() -> {
                throw new WsException(WsCode.ADDRESS_NOT_FOUND);
            });

            List<CartResponse> cart = repository.cartRepository.getListCart(currentUser.getId());

            if (cart.isEmpty()) {
                throw new WsException(WsCode.CART_IS_EMPTY);
            }

            String orderCode = "#".concat(String.valueOf(repository.orderRepository.getNextCodeOrder()));
            PaymentEnums payment = PaymentEnums.from(payload.getPaymentMethod());

            if (payment == null) {
                throw new WsException(WsCode.PAYMENT_NOT_FOUND);
            }



            OrderEntity order;

            if(payload.getDiscountCode() == null || payload.getDiscountCode().equalsIgnoreCase("")){
                order = OrderEntity.builder()
                        .id(UidUtils.generateUid())
                        .addressId(address.getId())
                        .userId(currentUser.getId())
                        .note(payload.getNote())
                        .payment(payload.getPaymentMethod())
                        .shipPrice(Long.valueOf(payload.getShipPrice()))
                        .payment(payment == null ? null : payment.name())
                        .shipPrice(Long.valueOf(payload.getShipPrice()))
                        .shipMethod(payload.getShipMethod())
                        .total(payload.getTotal())
                        .code(orderCode)
                        .payed(Boolean.FALSE)
                        .status(StatusEnum.PENDING.name())
                        .createdDate(new Date())
                        .updatedDate(new Date())
                        .createdBy(currentUser.getCombinationName())
                        .updatedBy(currentUser.getCombinationName())
                        .shopPrice(payload.getShopPrice())
                        .shipPriceDiscount(0L)
                        .shopPriceDiscount(0L)
                        .build();

            }else{
                DiscountEntity dc = repository.discountRepository.findByCode(payload.getDiscountCode());
                order = OrderEntity.builder()
                        .id(UidUtils.generateUid())
                        .addressId(address.getId())
                        .userId(currentUser.getId())
                        .note(payload.getNote())
                        .payment(payload.getPaymentMethod())
                        .shipPrice(Long.valueOf(payload.getShipPrice()))
                        .payment(payment == null ? null : payment.name())
                        .shipPrice(Long.valueOf(payload.getShipPrice()))
                        .shipMethod(payload.getShipMethod())
                        .total(payload.getTotal())
                        .code(orderCode)
                        .payed(Boolean.FALSE)
                        .status(StatusEnum.PENDING.name())
                        .createdDate(new Date())
                        .updatedDate(new Date())
                        .createdBy(currentUser.getCombinationName())
                        .updatedBy(currentUser.getCombinationName())
                        .shipPriceDiscount(payload.getShipPriceDiscount())
                        .shopPriceDiscount(payload.getShopPriceDiscount())
                        .shopPrice(payload.getShopPrice())
                        .discountId(dc.getId())
                        .build();
            }



            log.info("----- OrderServiceImpl create before save: {}", JsonUtils.toJson(order));
            repository.orderRepository.save(order);

            log.info("----- OrderServiceImpl create before save: {}", JsonUtils.toJson(order));


            repository.orderStatusRepository.save(OrderStatusEntity.builder()
                    .id(UUID.randomUUID().toString())
                    .orderId(order.getId())
                    .status(StatusEnum.PENDING.name())
                    .createdDate(new Date())
                    .createdBy(currentUser.getId())
                    .build());

            //update số lượng sản phảm sau khi đã update
            cart.stream().map(item -> {
                ProductOptionEntity productOption = repository.productOptionRepository.findById(item.getProductOptionId()).orElseThrow(() -> {
                    throw new WsException(WsCode.PRODUCT_OPTION_NOT_FOUND);
                });

                productOption.setQty(productOption.getQty() - item.getQuantity());

                return repository.productOptionRepository.save(productOption);

            }).collect(Collectors.toList());



            if(payload.getDiscountCode() == null || payload.getDiscountCode().equalsIgnoreCase("")){
                //save order to orderDetail
                cart.stream().map(item -> {
                    OrderDetailEntity orderDetail = OrderDetailEntity.builder()
                            .id(UidUtils.generateUid())
                            .orderId(order.getId())
                            .productOptionId(item.getProductOptionId())
                            .price(item.getPrice())
                            .qty(item.getQuantity())
                            .build();

                    return repository.orderDetailRepository.save(orderDetail);

                }).collect(Collectors.toList());
            }else {
                payload.getCart().stream().map(item -> {
                    OrderDetailEntity orderDetail = OrderDetailEntity.builder()
                            .id(UidUtils.generateUid())
                            .orderId(order.getId())
                            .productOptionId(item.getProductOptionId())
                            .price(item.getPrice())
                            .qty(item.getQuantity())
                            .discount(item.getDiscount())
                            .build();

                    return repository.orderDetailRepository.save(orderDetail);

                }).collect(Collectors.toList());
            }

            repository.cartRepository.clearCart(currentUser.getId());

            NotificationEntity notification = NotificationUtils.buildNotification(
                    String.format(NotificationUtils.CUSTOMER_CHECKOUT_TEMP, currentUser.getCombinationName()),
                    ObjectTypeEnum.ORDER.name(),
                    order.getId(),
                    NotificationTypeEnum.NORMAL.name()
            );

            repository.notificationRepository.save(notification);

            UserEntity user = repository.userRepository.findById(currentUser.getId()).orElseThrow(() -> {
                throw new WsException(WsCode.USER_NOT_FOUND);
            });

            //sendMail4OrderInfo
            this.sendMail4OrderInfo(order, user, address, cart, user.getEmail());

            //service

            return new ResData<>(order.getId(), WsCode.CREATED);
        }

    }


    public Object sendMail4OrderInfo(OrderEntity order, UserEntity user, AddressEntity address, List<CartResponse> cart, String email) {
        if (StringUtils.isNullOrEmpty(email)) {
            throw new WsException(WsCode.EMAIL_NOT_BLANK);
        }
        UserEntity customer = repository.userRepository.findByEmailIgnoreCaseAndActiveAndRole(email.trim(), Boolean.TRUE, RoleEnum.ROLE_CUSTOMER);
        log.info("-----  sendMail4OrderInfo : {}", JsonUtils.toJson(customer));
        if (customer == null) {
            throw new WsException(WsCode.ERROR_NOT_FOUND);
        }

        Map orderInfo = orderInfo(order, user, address, cart);

        try {
            Template template = configuration.getTemplate(ORDER_INFO_TEMPLATE_NAME);
            OrderInfoMail orderIn4 = OrderInfoMail.builder()
                    .from(String.format(FROM, email))
                    .to(customer.getEmail())
                    .text(FreeMarkerTemplateUtils.processTemplateIntoString(template, orderInfo))
                    .subject(SUBJECT + order.getCode() + " từ FLONE - LOOK GOOD, FEEL GOOD")
                    .build();
            log.info("----- sendMail4OrderInfo build : {}", JsonUtils.toJson(orderIn4));
            mailService.send4OrderInfo(orderIn4).get();
            return ResData.ok(order.getId());
        } catch (Exception e) {
            log.error("----- sendMail4OrderInfo error: {}", e.getMessage());
            throw new WsException(WsCode.INTERNAL_SERVER);
        }
    }


    private static Map orderInfo(OrderEntity order, UserEntity user, AddressEntity address, List<CartResponse> cart) {
        Map<String, Object> map = new HashMap<>();

        map.put(NAME, user.getFirstName() + " " + user.getLastName());
        map.put(EMAIL, user.getEmail());
        map.put(PHONE, user.getPhone());
        map.put(ADDRESS, address.getCombination());
        map.put(NAME_OF_RECIPIENT, address.getNameOfRecipient());
        map.put(PHONE_OF_RECIPIENT, address.getPhoneNumber());
        map.put(PAYMENT_METHOD, order.getPayment());
        map.put(SHIP_METHOD, order.getShipMethod());
        map.put(ORDER_CODE, order.getCode());
        map.put(CREATE_DATE, DateUtils.toStr(order.getCreatedDate(), DateUtils.F_DDMMYYYYHHMM));
        map.put(SHIP_PRICE, order.getShipPrice());
        map.put(TOTAL, order.getTotal());
        map.put(NOTE, order.getNote());
        map.put(PRODUCT, cart);

        map.put(SHIP_PRICE_DISCOUNT,order.getShipPriceDiscount());
        map.put(SHOP_PRICE_DISCOUNT,order.getShopPriceDiscount());
        map.put(TOTAL_DISCOUNT,order.getShopPriceDiscount() + order.getShipPriceDiscount());

//        if(order.getShipPriceDiscount() != null){
//
//            map.put(SHIP_PRICE_DISCOUNT,order.getShipPriceDiscount());
//        }else{
//            map.put(SHIP_PRICE_DISCOUNT,0);
//        }
//
//        if(order.getShopPriceDiscount() != null){
//
//            map.put(SHOP_PRICE_DISCOUNT,order.getShopPriceDiscount());
//        }else{
//            map.put(SHOP_PRICE_DISCOUNT,0);
//        }
//
//        if(order.getShopPriceDiscount() != null && order.getShipPriceDiscount() != null){
//            map.put(TOTAL_DISCOUNT,order.getShopPriceDiscount() + order.getShipPriceDiscount());
//        }else{
//            map.put(TOTAL_DISCOUNT,0);
//        }


        log.info("sendMail4Checkout() build model: {}", JsonUtils.toJson(map));

        return map;
    }
}
