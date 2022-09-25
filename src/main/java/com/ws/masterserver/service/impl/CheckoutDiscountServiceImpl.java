package com.ws.masterserver.service.impl;

import com.ws.masterserver.dto.admin.discount.prerequisite.QtyPrerequisiteType;
import com.ws.masterserver.dto.admin.discount.prerequisite.TotalPrerequisiteType;
import com.ws.masterserver.dto.admin.discount.type.PercentTypeDto;
import com.ws.masterserver.dto.admin.discount.type.PriceTypeDto;
import com.ws.masterserver.dto.admin.discount.type.ShipTypeDto;
import com.ws.masterserver.dto.customer.cart.response.CartResponse;
import com.ws.masterserver.dto.customer.order.checkout.CheckoutDiscountItem;
import com.ws.masterserver.dto.customer.order.checkout.CheckoutDiscountReq;
import com.ws.masterserver.dto.customer.order.checkout.CheckoutDiscountRes;
import com.ws.masterserver.entity.DiscountEntity;
import com.ws.masterserver.entity.DiscountProductEntity;
import com.ws.masterserver.service.CheckoutDiscountService;
import com.ws.masterserver.utils.base.WsException;
import com.ws.masterserver.utils.base.WsRepository;
import com.ws.masterserver.utils.base.rest.CurrentUser;
import com.ws.masterserver.utils.base.rest.ResData;
import com.ws.masterserver.utils.common.JsonUtils;
import com.ws.masterserver.utils.common.MoneyUtils;
import com.ws.masterserver.utils.common.StringUtils;
import com.ws.masterserver.utils.constants.WsCode;
import com.ws.masterserver.utils.constants.enums.*;
import com.ws.masterserver.utils.validator.auth.AuthValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CheckoutDiscountServiceImpl implements CheckoutDiscountService {
    private final WsRepository repository;

    @Override
    public Object check(CurrentUser currentUser, CheckoutDiscountReq payload) {
        log.info("check() payload: {}", JsonUtils.toJson(payload));
        AuthValidator.checkCustomerAndStaff(currentUser);
        if (StringUtils.isNullOrEmpty(payload.getDiscountCode()) || StringUtils.isNullOrEmpty(payload.getShopPrice())) {
            return null;
        }
        List<CartResponse> cart = repository.cartRepository.getListCart(currentUser.getId());
        if (cart.isEmpty()) {
            return null;
        }
        DiscountEntity discount = repository.discountRepository.findByCode(payload.getDiscountCode());
        if (discount == null) {
            throw new WsException(WsCode.DISCOUNT_INVALID);
        }
        Long shopPrice = this.getShopPriceFromCart(cart);
        this.validDiscount(discount, cart, currentUser.getId(), payload);
        CheckoutDiscountRes res = new CheckoutDiscountRes();
        List<Long> shopAndShipDiscount = this.getShopAndShipDiscount(discount, shopPrice, Long.valueOf(payload.getShipPrice()), cart, repository);
        Long shopDiscount = shopAndShipDiscount.get(0);
        Long shipDiscount = shopAndShipDiscount.get(1);
        long shipTotal = Math.max(Long.parseLong(payload.getShipPrice()) - shipDiscount, 0L);
        long shopTotal = Math.max(shopPrice - shopDiscount, 0L);
        return ResData.ok(CheckoutDiscountRes.builder()
                .ship(CheckoutDiscountItem.builder()
                        .raw(MoneyUtils.convertToBC(payload.getShipPrice()))
                        .discount(MoneyUtils.convertToBC(shipDiscount))
                        .total(MoneyUtils.convertToBC(shipTotal))
                        .build())
                .shop(CheckoutDiscountItem.builder()
                        .raw(MoneyUtils.convertToBC(shopPrice))
                        .discount(MoneyUtils.convertToBC(shopDiscount))
                        .total(MoneyUtils.convertToBC(shopTotal))
                        .build())
                .total(MoneyUtils.convertToBC(shopTotal + shipTotal))
                .build());
    }

    private Long getShopPriceFromCart(List<CartResponse> cartResponses) {
        return cartResponses.stream().mapToLong(o -> o.getQuantity() * o.getPrice()).reduce(0, Long::sum);
    }


    private void validDiscount(DiscountEntity discount, List<CartResponse> cart, String userId, CheckoutDiscountReq payload) {
        /**
         * kiểm tra mã đã hết hạn hay chưa
         * */
        if (discount.getEndDate() != null &&
                discount.getEndDate().after(new Date())) {
            throw new WsException(WsCode.DISCOUNT_HAS_EXPIRED);
        }
        DiscountStatusEnums discountStatusEnums = DiscountStatusEnums.from(discount.getStatus());
        if (!DiscountStatusEnums.ACTIVE.equals(discountStatusEnums)) {
            throw new WsException(WsCode.DISCOUNT_HAS_EXPIRED);
        }
        DiscountTypeEnums type = DiscountTypeEnums.from(discount.getType());
        switch (type) {
            case SHIP:
                /**
                 * Nếu mã km là loại miễn phí vận chuyển.
                 * Kiểm tra có truyền lên shipPrice
                 * Nếu shipPrice >= giá áp dụng với phí vận chuyển để mã km active => false
                 * */
                long shipPrice = Long.parseLong(payload.getShipPrice());
                if (StringUtils.isNullOrEmpty(payload.getShipPrice()) ||
                        !StringUtils.isOnlyNumber(payload.getShipPrice()) ||
                        shipPrice <= 0) {
                    break;
                }
                ShipTypeDto shipDto = JsonUtils.fromJson(type.name(), ShipTypeDto.class);
                if (!StringUtils.isNullOrEmpty(shipDto.getMaximumShippingRate()) &&
                        !StringUtils.isNullOrEmpty(payload.getShipPrice())) {
                    long maximumShippingRateValue = Long.valueOf(shipDto.getMaximumShippingRate());
                    if (shipPrice >= maximumShippingRateValue) {
                        throw new WsException(WsCode.SHIP_PRICE_UNSATISFACTORY);
                    }
                }
                break;
            case PERCENT:
            case PRICE:
            default:
                break;
        }

//        /**
//         * Kiểm tra ap dụng cho
//         * */
//        ApplyTypeEnums applyTypeEnum = ApplyTypeEnums.from(discount.getApplyType());
//        boolean isError = true;
//        switch (applyTypeEnum) {
//            /**
//             * danh mục sản phẩm
//             * kiểm tra xem các sản phẩm trong giỏ hàng có thuộc danh mục đó không
//             * */
//            case CATEGORY:
//                //danh sach danh muc duoc ap dung ma km
//                List<String> categoryIdAvailables = repository.discountCategoryRepository
//                        .findByDiscountId(discount.getId())
//                        .stream().map(DiscountCategoryEntity::getCategoryId).collect(Collectors.toList());
//
//                //danh sach danh muc của cac san pham co trong gio hang
//                List<String> cartCategoryIds = cart.stream().map(CartResponse::getCategoryId).distinct().collect(Collectors.toList());
//
//                for (String i : cartCategoryIds) {
//                    //neu sản pham có trong giỏ hanfg thuoc danh muc ap dung cho ma km => true
//                    if (categoryIdAvailables.contains(i)) {
//                        isError = false;
//                        break;
//                    }
//                }
//                break;
//
//            /**
//             * sản phẩm
//             * kiểm tra xem các sản phẩm trong giỏ hàng có thuộc danh sách đó không
//             * */
//            case ALL_PRODUCT:
//            case PRODUCT:
//                List<String> productIdAvailables = repository.discountProductRepository
//                        .findByDiscountId(discount.getId())
//                        .stream().map(DiscountProductEntity::getProductId).collect(Collectors.toList());
//                List<String> cartProductIds = cart.stream().map(CartResponse::getProductId).distinct().collect(Collectors.toList());
//                for (String i : cartProductIds) {
//                    if (productIdAvailables.contains(i)) {
//                        isError = false;
//                        break;
//                    }
//                }
//                break;
//            default:
//                break;
//        }
//        if (isError) {
//            throw new WsException(WsCode.DISCOUNT_HAS_NO_EFFECT_ON_YOUR_ORDER);
//        }

        /**
         * Kiểm tra điều kiện
         * */
        DiscountPrerequisiteTypeEnums prerequisiteEnum = DiscountPrerequisiteTypeEnums.from(discount.getPrerequisiteType());
        switch (prerequisiteEnum) {
            /**
             *Tổng số lượng sản phẩm được khuyến mãi tối thiếu
             * Kiểm tra số luwojgn sản phẩm có trong giỏ hàng có đủ k
             * */
            case QTY:
                QtyPrerequisiteType qty = JsonUtils.fromJson(prerequisiteEnum.name(), QtyPrerequisiteType.class);
                Integer totalQty = cart.stream().map(CartResponse::getQuantity).reduce(0, Integer::sum);
                if (totalQty < Integer.parseInt(qty.getMinimumQuantity())) {
                    throw new WsException(WsCode.MIN_QTY_INVALID);
                }
                break;
            /**
             * Tổng giá trị đơn hàng tối thiểu
             * Kiểm tra Tổng giá trị đơn hàng có trong giỏ hàng có đủ k
             * */
            case TOTAL:
                TotalPrerequisiteType total = JsonUtils.fromJson(prerequisiteEnum.name(), TotalPrerequisiteType.class);
                Long totalPriceInCart = this.getShopPriceFromCart(cart);
                if (totalPriceInCart < Long.parseLong(total.getMinimumSaleTotalPrice())) {
                    throw new WsException(WsCode.MIN_SALE_INVALID);
                }
                break;
            case NONE:
            default:
                break;
        }

        /**
         * kiểm tra khách hàng có nằm trong danh sách khách hàng được sử dụng mã KM hay k
         * */
        DiscountCustomerTypeEnums customerTypeEnum = DiscountCustomerTypeEnums.from(discount.getCustomerType());
        switch (customerTypeEnum) {
            case GROUP:
                if (!repository.discountCustomerRepository.checkCustomerAvailableCaseGroup(discount.getId(), userId)) {
                    throw new WsException(WsCode.DISCOUNT_INVALID);
                }
                break;
            case CUSTOMER:
                if (!repository.discountCustomerRepository.checkCustomerAvailableCaseCustomer(discount.getId(), userId)) {
                    throw new WsException(WsCode.DISCOUNT_INVALID);
                }
                break;
            case ALL:
            default:
                break;
        }

        /**
         * Kiểm tra xem mã KM đã dùng hết chưa
         * */
        if (discount.getUsageLimit() != null) {
            Long totalUsageNumber = repository.discountRepository.getUsageNumberNow(discount.getId());
            if (totalUsageNumber >= discount.getUsageLimit()) {
                throw new WsException(WsCode.DISCOUNT_LIMIT_USAGE);
            }
        }

        /**
         * Kiểm tra xem KH đã dùng mã KM chưa
         * */
        if (discount.getOncePerCustomer() &&
                repository.orderRepository.checkCustomerHasUsedDiscount(userId, discount.getId())) {
            throw new WsException(WsCode.DISCOUNT_INVALID);
        }

    }


    private List<Long> getShopAndShipDiscount(DiscountEntity discount, Long shopPrice, Long shipPrice, List<CartResponse> cart, WsRepository repository) {
        DiscountTypeEnums discountTypeEnums = DiscountTypeEnums.from(discount.getType());
        ApplyTypeEnums applyTypeEnums = ApplyTypeEnums.from(discount.getApplyType());
        String typeJsonValue = discount.getTypeValue();
        Long shopDiscount = 0L;
        Long shipDiscount = 0L;

        switch (discountTypeEnums) {
            case PERCENT:
                PercentTypeDto percentDto = JsonUtils.fromJson(typeJsonValue, PercentTypeDto.class);
//                shopDiscount = shopPrice * Long.parseLong(percentDto.getPercentageValue()) / 100;
//                if (!StringUtils.isNullOrEmpty(percentDto.getValueLimitAmount())) {
//                    Long valueLimitAmount = Long.valueOf(percentDto.getValueLimitAmount());
//                    if (shopDiscount > valueLimitAmount) {
//                        shopDiscount = valueLimitAmount;
//                    }
//                }
                switch (applyTypeEnums) {
                    case ALL_PRODUCT:
                    case PRODUCT:
                        List<String> productIdApplyTypeIds = repository.discountProductRepository.findByDiscountId(discount.getId())
                                .stream().map(DiscountProductEntity::getProductId).collect(Collectors.toList());
                        for (CartResponse cartResponse : cart) {
                            if (productIdApplyTypeIds.contains(cartResponse.getProductId())) {

                            }
                        }
                        break;
                    case CATEGORY:

                        break;
                    default:

                        break;
                }


                break;
            case PRICE:
                PriceTypeDto priceDto = JsonUtils.fromJson(typeJsonValue, PriceTypeDto.class);
                shopDiscount = Long.valueOf(priceDto.getAmountValue());
                break;
            case SHIP:
                ShipTypeDto shipDto = JsonUtils.fromJson(typeJsonValue, ShipTypeDto.class);
                if (StringUtils.isNullOrEmpty(shipDto.getShipValueLimitAmount()) && shipPrice > 0) {
                    shipDiscount = Long.valueOf(shipDto.getShipValueLimitAmount());
                }
                break;
            default:
                break;
        }
        return Arrays.asList(shopDiscount, shipDiscount);
    }

}
