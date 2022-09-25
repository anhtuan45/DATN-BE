package com.ws.masterserver.utils.common;

import com.ws.masterserver.dto.admin.discount.prerequisite.QtyPrerequisiteType;
import com.ws.masterserver.dto.admin.discount.prerequisite.TotalPrerequisiteType;
import com.ws.masterserver.dto.admin.discount.type.PercentTypeDto;
import com.ws.masterserver.dto.admin.discount.type.PriceTypeDto;
import com.ws.masterserver.dto.admin.discount.type.ShipTypeDto;
import com.ws.masterserver.entity.DiscountEntity;
import com.ws.masterserver.utils.base.WsRepository;
import com.ws.masterserver.utils.constants.enums.*;

import java.util.ArrayList;
import java.util.List;

public class DiscountUtils {
    private static final String SPACE = " ";

    public static List<String> getDes(DiscountEntity discount, WsRepository repository) {
        List<String> result = new ArrayList<>();
        DiscountTypeEnums discountType = DiscountTypeEnums.from(discount.getType());
        addTypeStr(discount, result);
        if (!DiscountTypeEnums.SHIP.equals(discountType)) {
            addApplyTypeStr(discount, repository, result);
            addPrerequisiteStr(discount, result);
        }
        addCustomerStr(discount, repository, result);
        addUsageLimitStr(discount, result);
        addOncePerCustomerStr(discount, result);
        return result;
    }

    private static void addOncePerCustomerStr(DiscountEntity discount, List<String> result) {
        if (discount.getOncePerCustomer() != null && discount.getOncePerCustomer()) {
            result.add("Giới hạn mỗi khách hàng chỉ được sử dụng mã giảm giá này 1 lần");
        }
    }

    private static void addUsageLimitStr(DiscountEntity discount, List<String> result) {
        String prefix = "Giới hạn số lần mã giảm giá được áp dụng: ";
        if (discount.getUsageLimit() == null) {
            prefix += "Không giới hạn";
        } else {
            prefix += discount.getUsageLimit();
        }
        result.add(prefix);
    }

    private static void addCustomerStr(DiscountEntity discount, WsRepository repository, List<String> result) {
        String customerTypeStr = "Áp dụng cho: ";
        DiscountCustomerTypeEnums customerTypeEnum = DiscountCustomerTypeEnums.from(discount.getCustomerType());
        String customerTypeEnumName = customerTypeEnum.getName();
        switch (customerTypeEnum) {
            case ALL:
                customerTypeStr += customerTypeEnumName;
                break;
            case CUSTOMER:
                customerTypeStr += repository.discountCustomerRepository.countByDiscountId(discount.getId()) + SPACE + customerTypeEnumName;
                break;
            case GROUP:
                customerTypeStr += repository.discountCustomerTypeRepository.countByDiscountId(discount.getId()) +
                        SPACE + customerTypeEnumName + SPACE +
                        "(" + repository.discountCustomerRepository.countByDiscountId(discount.getId()) + SPACE + DiscountCustomerTypeEnums.CUSTOMER.getName() + ")";
                break;
            default:
                break;
        }
        result.add(customerTypeStr);
    }

    private static void addPrerequisiteStr(DiscountEntity discount, List<String> result) {
        String prerequisiteStr = "Điều kiện: ";
        DiscountPrerequisiteTypeEnums prerequisteType = DiscountPrerequisiteTypeEnums.from(discount.getPrerequisiteType());
        switch (prerequisteType) {
            case NONE:
                prerequisiteStr += "Không";
                break;
            case TOTAL:
                TotalPrerequisiteType total = JsonUtils.fromJson(discount.getPrerequisiteValue(), TotalPrerequisiteType.class);
                prerequisiteStr += prerequisteType.getName() + SPACE + MoneyUtils.formatV2(total.getMinimumSaleTotalPrice());
                break;
            case QTY:
                QtyPrerequisiteType qty = JsonUtils.fromJson(discount.getPrerequisiteValue(), QtyPrerequisiteType.class);
                prerequisiteStr += prerequisteType.getName() + ": " + Long.valueOf(qty.getMinimumQuantity());
                break;
            default:
                break;
        }
        result.add(prerequisiteStr);
    }

    private static void addApplyTypeStr(DiscountEntity discount, WsRepository repository, List<String> result) {
        ApplyTypeEnums applyTypeEnum = ApplyTypeEnums.from(discount.getApplyType());
        String applyTypeStr = "Áp dụng cho: ";
        switch (applyTypeEnum) {
            case ALL_PRODUCT:
                applyTypeStr += ApplyTypeEnums.ALL_PRODUCT.getName();
                break;
            case PRODUCT:
                applyTypeStr += repository.discountProductRepository.countByDiscountId(discount.getId()) + " Sản phẩm";
                break;
            case CATEGORY:
                applyTypeStr += repository.discountCategoryRepository.countByDiscountId(discount.getId()) + " Danh mục (" +
                        repository.discountProductRepository.countByDiscountId(discount.getId()) + " sản phẩm)";
                break;
            default:
                break;
        }
        result.add(applyTypeStr);
    }

    private static void addTypeStr(DiscountEntity discount, List<String> result) {
        DiscountTypeEnums typeEnum = DiscountTypeEnums.from(discount.getType());
        String typeStr = "Giảm" + SPACE;
        switch (typeEnum) {
            case PERCENT:
                PercentTypeDto percent = JsonUtils.fromJson(discount.getTypeValue(), PercentTypeDto.class);
                typeStr += percent.getPercentageValue() + "%";
                if (!StringUtils.isNullOrEmpty(percent.getValueLimitAmount())) {
                    typeStr += String.format(" (tối đa %s)", MoneyUtils.formatV2(percent.getValueLimitAmount()));
                }
                break;
            case PRICE:
                PriceTypeDto price = JsonUtils.fromJson(discount.getTypeValue(), PriceTypeDto.class);
                typeStr += MoneyUtils.formatV2(price.getAmountValue());
                break;
            case SHIP:
                ShipTypeDto ship = JsonUtils.fromJson(discount.getTypeValue(), ShipTypeDto.class);
                typeStr = "Miễn phí vận chuyển";
                if (!StringUtils.isNullOrEmpty(ship.getShipValueLimitAmount())) {
                    typeStr += String.format(" tối đa %s", MoneyUtils.formatV2(ship.getShipValueLimitAmount()));
                }
                if (!StringUtils.isNullOrEmpty(ship.getMaximumShippingRate())) {
                    typeStr += String.format(" (với đơn hàng có phí vận chuyển dưới %s)", MoneyUtils.formatV2(ship.getMaximumShippingRate()));
                }
        }
        result.add(typeStr);
    }
}
