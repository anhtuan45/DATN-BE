package com.ws.masterserver.utils.validator.admin.discount;

import com.ws.masterserver.dto.admin.discount.create.DiscountDto;
import com.ws.masterserver.dto.admin.discount.prerequisite.PrerequisiteDto;
import com.ws.masterserver.dto.admin.discount.prerequisite.QtyPrerequisiteType;
import com.ws.masterserver.dto.admin.discount.prerequisite.TotalPrerequisiteType;
import com.ws.masterserver.dto.admin.discount.type.PercentTypeDto;
import com.ws.masterserver.dto.admin.discount.type.PriceTypeDto;
import com.ws.masterserver.dto.admin.discount.type.ShipTypeDto;
import com.ws.masterserver.utils.base.WsException;
import com.ws.masterserver.utils.base.WsRepository;
import com.ws.masterserver.utils.common.DateUtils;
import com.ws.masterserver.utils.common.StringUtils;
import com.ws.masterserver.utils.common.ValidatorUtils;
import com.ws.masterserver.utils.constants.WsCode;
import com.ws.masterserver.utils.constants.enums.*;
import com.ws.masterserver.utils.constants.field.DiscountFields;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author myname
 */
@Slf4j
public class AdminDiscountValidator {
    private AdminDiscountValidator() {
        super();
    }

    public static void validCreateDtoData(DiscountDto payload) throws WsException {
        ValidatorUtils.validNullOrEmpty(DiscountFields.CODE, payload.getCode());
        ValidatorUtils.validNotContainSpace(DiscountFields.CODE, payload.getCode());
        ValidatorUtils.validLength(DiscountFields.CODE, payload.getCode(), 4, 50);
        ValidatorUtils.validOnlyCharacterAndNumber(DiscountFields.CODE, payload.getCode());

        //loai khuyen mai
        ValidatorUtils.validNullOrEmpty(DiscountFields.TYPE, payload.getType());
        validDiscountTypeValue(payload);

        DiscountTypeEnums discountTypeEnums = DiscountTypeEnums.from(payload.getType());
        //dieu kien ap dung
        //chi valid voi loai KM khac van chuyen
        if (!DiscountTypeEnums.SHIP.equals(discountTypeEnums)) {
            ValidatorUtils.validNullOrEmpty(DiscountFields.PREREQUISITE, payload.getPrerequisiteType());
            validPrerequisiteValue(payload.getPrerequisiteType(), payload.getPrerequisiteTypeValue());
        }

        //nhoimm KH
        ValidatorUtils.validNullOrEmpty(DiscountFields.APPLY_CUSTOMER_TYPE, payload.getCustomerType());
        validCustomerTypeValue(payload.getCustomerType(), payload.getCustomerIds());

        //gioi han su dung
        if (!StringUtils.isNullOrEmpty(payload.getUsageLimit())) {
            ValidatorUtils.validOnlyNumber(DiscountFields.USAGE_LIMIT, payload.getUsageLimit());
            ValidatorUtils.validLongValueMustBeMore(DiscountFields.USAGE_LIMIT, payload.getUsageLimit(), 0L);
        }

        //thoi gian
        validTime(payload.getStartDate(), payload.getHasEndDate(), payload.getEndDate());
    }

    private static void validTime(String startDate, Boolean hasEndsDate, String endDate) {
        ValidatorUtils.validNullOrEmpty(DiscountFields.START_TIME, startDate);
        ValidatorUtils.validDateTimeFormat(DiscountFields.START_TIME, startDate, DateUtils.F_DDMMYYYYHHMM);
        if (hasEndsDate) {
            ValidatorUtils.validNullOrEmpty(DiscountFields.END_TIME, endDate);
            ValidatorUtils.validDateTimeFormat(DiscountFields.END_TIME, endDate, DateUtils.F_DDMMYYYYHHMM);
            ValidatorUtils.validEndNotMoreStart(DiscountFields.START_TIME, startDate, DiscountFields.END_TIME, endDate, DateUtils.F_DDMMYYYYHHMM);
        }
    }

    private static void validCustomerTypeValue(String customerType, List<String> customerTypeIds) {
        DiscountCustomerTypeEnums type = DiscountCustomerTypeEnums.from(customerType);
        switch (type) {
            case GROUP:
                ValidatorUtils.validNullOrEmptyStringList(DiscountFields.GROUP_CUSTOMER, customerTypeIds);
                break;
            case CUSTOMER:
                ValidatorUtils.validNullOrEmptyStringList(DiscountFields.CUSTOMER, customerTypeIds);
                break;
            case ALL:
            default:
                break;
        }
    }

    private static void validPrerequisiteValue(String prerequisiteType, PrerequisiteDto dto) {
        DiscountPrerequisiteTypeEnums preType = DiscountPrerequisiteTypeEnums.from(prerequisiteType);
        if (null == preType) {
            throw new WsException(WsCode.MUST_SELECT_PREREQUISITE);
        }
        switch (preType) {
            case TOTAL:
                TotalPrerequisiteType total = (TotalPrerequisiteType) dto;
                ValidatorUtils.validNullOrEmpty(DiscountFields.MINIMUM_SUB_TOTAL_PRICE, total.getMinimumSaleTotalPrice());
                ValidatorUtils.validLongValueMustBeMore(DiscountFields.MINIMUM_SUB_TOTAL_PRICE, total.getMinimumSaleTotalPrice(), 0L);
                break;
            case QTY:
                QtyPrerequisiteType qty = (QtyPrerequisiteType) dto;
                ValidatorUtils.validNullOrEmpty(DiscountFields.MINIMUM_QTY, qty.getMinimumQuantity());
                ValidatorUtils.validLongValueMustBeMore(DiscountFields.MINIMUM_QTY, qty.getMinimumQuantity(), 0L);
                break;
            default:
                break;
        }
    }

    private static void validDiscountTypeValue(DiscountDto payload) {
        DiscountTypeEnums type = DiscountTypeEnums.from(payload.getType());
        if (type == null) {
            throw new WsException(WsCode.MUST_SELECT_DISCOUNT_TYPE);
        }
        switch (type) {
            case PERCENT:
                validPercentType(payload.getTypeValue());
                break;
            case PRICE:
                validPriceType(payload.getTypeValue());
                break;
            case SHIP:
                validShipType(payload.getTypeValue());
                break;
            default:
                throw new WsException(WsCode.MUST_SELECT_DISCOUNT_TYPE);
        }

        if (!DiscountTypeEnums.SHIP.equals(type)) {
            ApplyTypeEnums applyType = ApplyTypeEnums.from(payload.getApplyType());
            if (null == applyType) {
                throw new WsException(WsCode.MUST_SELECT_APPLY_TYPE);
            }
            switch (applyType) {
                case CATEGORY:
                    ValidatorUtils.validNullOrEmptyStringList(DiscountFields.CATEGORY_LIST, payload.getApplyTypeIds());
                    break;
                case PRODUCT:
                    ValidatorUtils.validNullOrEmptyStringList(DiscountFields.PRODUCT_LIST, payload.getApplyTypeIds());
                    break;
                case ALL_PRODUCT:
                default:
                    break;
            }
        }
    }

    private static void validShipType(Object typeValues) {
        if (typeValues instanceof ShipTypeDto) {
            ShipTypeDto dto = (ShipTypeDto) typeValues;
            String shipValueLimitAmount = dto.getShipValueLimitAmount();
            String maximumShippingRate = dto.getMaximumShippingRate();
            if (!StringUtils.isNullOrEmpty(shipValueLimitAmount)) {
                ValidatorUtils.validLongValueMustBeMore(DiscountFields.VALUE_LIMIT_AMOUNT_SHIP, shipValueLimitAmount, 0L);
            }
            if (!StringUtils.isNullOrEmpty(maximumShippingRate)) {
                ValidatorUtils.validLongValueMustBeMore(DiscountFields.MAXIMUM_SHIPPING_RATE, maximumShippingRate, 0L);
                if (!StringUtils.isNullOrEmpty(shipValueLimitAmount)) {
                    Long maximumShippingRateLong = Long.parseLong(maximumShippingRate);
                    Long shipValueLimitAmountLong = Long.parseLong(shipValueLimitAmount);
                    ValidatorUtils.validLongValueMustBeLess(DiscountFields.VALUE_LIMIT_AMOUNT_SHIP, shipValueLimitAmountLong, maximumShippingRateLong);
                }
            }
        } else {
            throw new WsException(WsCode.INTERNAL_SERVER);
        }
    }

    private static void validPriceType(Object typeValues) {
        if (typeValues instanceof PriceTypeDto) {
            PriceTypeDto dto = (PriceTypeDto) typeValues;
            ValidatorUtils.validNullOrEmpty(DiscountFields.AMOUNT_VALUE, dto.getAmountValue());
            ValidatorUtils.validLongPriceValueMustBeMore(DiscountFields.AMOUNT_VALUE, dto.getAmountValue(), 0L);
        } else {
            throw new WsException(WsCode.INTERNAL_SERVER);
        }
    }

    private static void validPercentType(Object typeValues) {
        PercentTypeDto dto = (PercentTypeDto) typeValues;
        ValidatorUtils.validNullOrEmpty(DiscountFields.AMOUNT_VALUE, dto.getPercentageValue());
        ValidatorUtils.validOnlyNumber(DiscountFields.AMOUNT_VALUE, dto.getPercentageValue());
        ValidatorUtils.validLongValueBetween(DiscountFields.AMOUNT_VALUE, dto.getPercentageValue(), 0L, 100L);
        if (!StringUtils.isNullOrEmpty(dto.getValueLimitAmount())) {
            ValidatorUtils.validOnlyNumber(DiscountFields.VALUE_LIMIT_AMOUNT_PERCENT, dto.getValueLimitAmount());
            ValidatorUtils.validLongPriceValueMustBeMore(DiscountFields.VALUE_LIMIT_AMOUNT_PERCENT, dto.getValueLimitAmount(), 0L);
        }
    }

    public static void validCreateDtoConstraint(DiscountDto payload, WsRepository repository) {
        if (repository.discountRepository.existsByCodeAndDeleted(payload.getCode().trim(), false)) {
            throw new WsException(WsCode.DISCOUNT_CODE_EXISTS);
        }
    }
}
