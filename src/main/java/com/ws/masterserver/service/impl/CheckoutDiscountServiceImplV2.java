package com.ws.masterserver.service.impl;

import com.ws.masterserver.dto.admin.discount.prerequisite.QtyPrerequisiteType;
import com.ws.masterserver.dto.admin.discount.prerequisite.TotalPrerequisiteType;
import com.ws.masterserver.dto.admin.discount.type.PercentTypeDto;
import com.ws.masterserver.dto.admin.discount.type.PriceTypeDto;
import com.ws.masterserver.dto.admin.discount.type.ShipTypeDto;
import com.ws.masterserver.dto.customer.cart.response.CartResponse;
import com.ws.masterserver.dto.customer.order.checkout.CheckoutDiscountReq;
import com.ws.masterserver.dto.customer.order.checkout.v2.CheckoutDiscountResV2;
import com.ws.masterserver.entity.*;
import com.ws.masterserver.service.CheckoutDiscountServiceV2;
import com.ws.masterserver.utils.base.WsException;
import com.ws.masterserver.utils.base.WsRepository;
import com.ws.masterserver.utils.base.rest.CurrentUser;
import com.ws.masterserver.utils.common.JsonUtils;
import com.ws.masterserver.utils.common.StringUtils;
import com.ws.masterserver.utils.constants.WsCode;
import com.ws.masterserver.utils.constants.enums.*;
import com.ws.masterserver.utils.validator.auth.AuthValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CheckoutDiscountServiceImplV2 implements CheckoutDiscountServiceV2 {

    private final WsRepository repository;

    @Override
    public Object verifyDiscountWhenCheckout(CurrentUser currentUser, CheckoutDiscountReq payload) {
        log.info("checkDiscount() payload: {}", JsonUtils.toJson(payload));
        AuthValidator.checkCustomerAndStaff(currentUser);
        String shipPrice = payload.getShipPrice();
        if (StringUtils.isNullOrEmpty(payload.getDiscountCode()) || StringUtils.isNullOrEmpty(shipPrice)) {
            return null;
        }
        List<CartResponse> cart = repository.cartRepository.getListCart(currentUser.getId());
        if (cart.isEmpty()) {
            return null;
        }

        DiscountEntity discount = repository.discountRepository.findByCodeAndDeleted(payload.getDiscountCode(), false);
        CheckoutDiscountResV2 res = new CheckoutDiscountResV2();
        this.validDiscount(discount, cart, currentUser, payload, res);

        /**
         * Convert cart
         */
        Long saleTotal = 0L;
        Long totalDiscount = 0L;
        for (CartResponse item : cart) {
            Long itemDiscount = 0L;
            if (item.getDiscount() != null) {
                itemDiscount = item.getDiscount();
            }
            Long itemSubTotal = item.getSubtotal() - itemDiscount;
            if (itemSubTotal < 0) {
                itemSubTotal = 0L;
            }
            item.setDiscount(itemDiscount);
            item.setSubtotal(itemSubTotal);
            totalDiscount += itemDiscount;
            saleTotal += itemSubTotal;
        }
        Long shipDiscount = 0L;
        if (res.getShipDiscount() != null) {
            shipDiscount = res.getShipDiscount();
        }
        res.setShipDiscount(shipDiscount);

        res.setCart(cart);
        res.setSaleDiscount(totalDiscount);
        res.setSaleTotal(saleTotal);
        long totalRes = saleTotal + Long.parseLong(shipPrice) - shipDiscount;
        res.setTotal(totalRes);
        res.setShip(Long.parseLong(shipPrice));
        return res;
    }

    private void validDiscount(DiscountEntity discount, List<CartResponse> cart, CurrentUser currentUser, CheckoutDiscountReq payload, CheckoutDiscountResV2 res) {
        if (discount == null) {
            log.info("validDiscount() discount null");
            throw new WsException(WsCode.DISCOUNT_INVALID);
        }
        this.validExpried(discount);

        this.validStatus(discount);

        String discountId = discount.getId();

        this.validAvailableCustomer(discount, currentUser, discountId);

        this.validUsageLimit(discount.getUsageLimit(), discountId);
        this.validOnePerCustomer(discount, currentUser.getId());


        ApplyTypeEnums applyType = ApplyTypeEnums.from(discount.getApplyType());
        List<String> productIdAvailableList = new ArrayList<>();

        this.validAvalableProduct(discountId, applyType, productIdAvailableList);

        DiscountTypeEnums discountType = DiscountTypeEnums.from(discount.getType());
        DiscountPrerequisiteTypeEnums prerequisiteType = DiscountPrerequisiteTypeEnums.from(discount.getPrerequisiteType());

        this.validPrerequisite(discount, cart, productIdAvailableList, discountType, prerequisiteType);

        //valid loại KM
        String discountTypeValue = discount.getTypeValue();
        switch (discountType) {
            /**
             * TH KM vận chuyển. Check xem phí vận chuyển của đơn hàng có thỏa mã DK của mã KM k
             * */
            case SHIP:
                Long shipPrice = Long.parseLong(payload.getShipPrice());
                ShipTypeDto shipTypeDto = JsonUtils.fromJson(discountTypeValue, ShipTypeDto.class);
                String maximumShippingRate = shipTypeDto.getMaximumShippingRate();
                if (StringUtils.isNullOrEmpty(maximumShippingRate)) {
                    res.setShipDiscount(shipPrice);
                } else {
                    Long maximumShippingRateLong = Long.parseLong(maximumShippingRate);
                    if (shipPrice > maximumShippingRateLong) {
                        log.info("validDiscount() shipPrice > maximumShippingRateLong");
                        throw new WsException(WsCode.SHIP_PRICE_UNSATISFACTORY);
                    }
                    String shipValueLimitAmount = shipTypeDto.getShipValueLimitAmount();
                    if (!StringUtils.isNullOrEmpty(shipValueLimitAmount)) {
                        Long shipValueLimitAmountLong = Long.parseLong(shipValueLimitAmount);
                        res.setShipDiscount(shipValueLimitAmountLong);
                    }
                }
                return;
            case PERCENT:
                PercentTypeDto percentTypeDto = JsonUtils.fromJson(discountTypeValue, PercentTypeDto.class);
                Long percentageValue = Long.parseLong(percentTypeDto.getPercentageValue());
                Long valueLimitAmount = 0L;
                if (!StringUtils.isNullOrEmpty(percentTypeDto.getValueLimitAmount())) {
                    valueLimitAmount = Long.parseLong(percentTypeDto.getValueLimitAmount());
                }
                for (CartResponse item : cart) {
                    if (productIdAvailableList.contains(item.getProductId())) {
                        Long itemDiscountPercentType = item.getPrice() * percentageValue / 100;
                        if (itemDiscountPercentType >= valueLimitAmount && itemDiscountPercentType > 0L) {
                            itemDiscountPercentType = valueLimitAmount;
                        }
                        item.setDiscount(itemDiscountPercentType);
                    }
                }
                break;
            case PRICE:
                PriceTypeDto priceTypeDto = JsonUtils.fromJson(discountTypeValue, PriceTypeDto.class);
                Long amountValue = Long.parseLong(priceTypeDto.getAmountValue());
                for (CartResponse item : cart) {
                    if (productIdAvailableList.contains(item.getProductId())) {
                        item.setDiscount(amountValue);
                    }
                }
            default:
                break;
        }
    }

    private void validOnePerCustomer(DiscountEntity discount, String userId) {
        if (discount.getOncePerCustomer() && repository.discountRepository.checkOnePerCustomerByUserId(discount.getId(), userId)) {
            log.error("validOnePerCustomer() used");
            throw new WsException(WsCode.DISCOUNT_INVALID);
        }
    }

    private static void validPrerequisite(DiscountEntity discount, List<CartResponse> cart, List<String> productIdAvailableList, DiscountTypeEnums discountType, DiscountPrerequisiteTypeEnums prerequisiteType) {
        /**
         * Check Th không phải loại KM vận chuyển thì check các dkien đơn hàng
         * */
        if (!DiscountTypeEnums.SHIP.equals(discountType)) {
            Long totalQty = 0L;
            Long totalSale = 0L;
            for (CartResponse item : cart) {
                if (productIdAvailableList.contains(item.getProductId())) {
                    /**
                     * Tổng số lượng và tổng giá trị các sản phẩm được KM có trong giỏ hàng
                     * */
                    totalQty += item.getQuantity();
                    totalSale += item.getSubtotal();
                }
            }
            String prerequisiteValue = discount.getPrerequisiteValue();
            switch (prerequisiteType) {
                case QTY:
                    QtyPrerequisiteType qtyPrerequisite = JsonUtils.fromJson(prerequisiteValue, QtyPrerequisiteType.class);
                    if (qtyPrerequisite == null) {
                        log.info("validDiscount() qtyPrerequisite null");
                        throw new WsException(WsCode.INTERNAL_SERVER);
                    }
                    long minimumQuantity = Long.parseLong(qtyPrerequisite.getMinimumQuantity());
                    if (totalQty < minimumQuantity) {
                        log.info("validDiscount() totalQty < minimumQuantity");
                        throw new WsException(WsCode.MIN_QTY_INVALID);
                    }
                    /**
                     * Nếu thỏa mãn
                     * */
                    break;
                case TOTAL:
                    TotalPrerequisiteType totalPrerequisite = JsonUtils.fromJson(prerequisiteValue, TotalPrerequisiteType.class);
                    if (totalPrerequisite == null) {
                        log.info("validDiscount() totalPrerequisite null");
                        throw new WsException(WsCode.INTERNAL_SERVER);
                    }
                    long minimumSale = Long.parseLong(totalPrerequisite.getMinimumSaleTotalPrice());
                    if (totalSale < minimumSale) {
                        log.info("validDiscount() totalSale < minimumSale");
                        throw new WsException(WsCode.MIN_SALE_INVALID);
                    }
                    break;
                case NONE:
                default:
                    break;
            }
        }
    }

    private void validAvalableProduct(String discountId, ApplyTypeEnums applyType, List<String> productIdAvailableList) {
        /**
         * Danh sách sản phẩm được áp dụng mã KM
         * */
        switch (applyType) {
            case ALL_PRODUCT:
                productIdAvailableList.addAll(repository.productRepository.findAllByActive(true).stream().map(ProductEntity::getId).collect(Collectors.toList()));
                break;
            case PRODUCT:
                productIdAvailableList.addAll(repository.discountProductRepository.findByDiscountIdAndProductActive(discountId).stream().map(DiscountProductEntity::getProductId).collect(Collectors.toList()));
                break;
            case CATEGORY:
                List<String> categoryIds = repository.discountCategoryRepository.findByDiscountIdAndCategoryActive(discountId).stream().map(DiscountCategoryEntity::getCategoryId).collect(Collectors.toList());
                for (String categoryId : categoryIds) {
                    productIdAvailableList.addAll(repository.productRepository.findByCategoryIdAndActive(categoryId, true).stream().map(ProductEntity::getId).collect(Collectors.toList()));
                }
            default:
                break;
        }
    }

    private void validUsageLimit(Long discountUsageLimit, String discountId) {
        /**
         * Kiểm tra xem mã đã dùng dùng hết lượt chưa
         * */
        if (discountUsageLimit != null) {
            Long usageLimitNow = repository.discountRepository.getUsageNumberNow(discountId);
            if (usageLimitNow >= discountUsageLimit) {
                log.info("validDiscount() usageLimitNow >= discountUsageLimit");
                throw new WsException(WsCode.DISCOUNT_LIMIT_USAGE);
            }
        }
    }

    private void validAvailableCustomer(DiscountEntity discount, CurrentUser currentUser, String discountId) {
        /**
         * Kiểm tra khách hàng có được sử dụng mã KM không
         * */
        DiscountCustomerTypeEnums discountCustomerTypeEnums = DiscountCustomerTypeEnums.from(discount.getCustomerType());
        Boolean isAvailableCustomer = false;
        switch (discountCustomerTypeEnums) {
            case GROUP:
                isAvailableCustomer = repository.discountCustomerRepository.checkCustomerAvailableCaseGroup(discountId, currentUser.getId());
                break;
            case CUSTOMER:
                isAvailableCustomer = repository.discountCustomerRepository.checkCustomerAvailableCaseCustomer(discountId, currentUser.getId());
                break;
            case ALL:
                isAvailableCustomer = true;
                break;
            default:
                break;
        }
        if (!isAvailableCustomer) {
            log.info("validDiscount() isAvailableCustomer false");
            throw new WsException(WsCode.DISCOUNT_INVALID);
        }
    }

    private void validStatus(DiscountEntity discount) {
        /**
         * Kiểm tra trạng thái hiện hại của mã KM
         * */
        DiscountStatusEnums statusNow = DiscountStatusEnums.from(discount.getStatus());
        if (!DiscountStatusEnums.ACTIVE.equals(statusNow)) {
            log.info("validDiscount() statusNow: {}", statusNow);
            throw new WsException(WsCode.DISCOUNT_HAS_EXPIRED);
        }
    }

    private void validExpried(DiscountEntity discount) {
        /**
         * kiểm tra mã đã hết hạn hay chưa
         * */
        Date endDate = discount.getEndDate();
        if (endDate == null) {
            return;
        }
        Long nowMils = new Date().getTime();
        Long endDateMils = endDate.getTime();
        if (endDateMils < nowMils) {
            log.info("validDiscount() endDate {} after now {}", endDateMils, nowMils);
            throw new WsException(WsCode.DISCOUNT_HAS_EXPIRED);
        }
    }
}
