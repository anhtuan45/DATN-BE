package com.ws.masterserver.utils.common;

import com.ws.masterserver.dto.admin.order.detail.PriceDto;
import com.ws.masterserver.dto.admin.order.detail.PromotionDto;
import com.ws.masterserver.dto.admin.order.detail.ResultDto;
import com.ws.masterserver.dto.admin.order.detail.StatusDto;
import com.ws.masterserver.dto.admin.order.search.OptionDto;
import com.ws.masterserver.entity.UserEntity;
import com.ws.masterserver.utils.base.WsException;
import com.ws.masterserver.utils.constants.WsCode;
import com.ws.masterserver.utils.constants.enums.PromotionTypeEnum;
import com.ws.masterserver.utils.constants.enums.RoleEnum;
import com.ws.masterserver.utils.constants.enums.StatusEnum;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class OrderUtils {

    public static final String ACCEPTED_NAME = "Chấp nhận đơn hàng";
    public static final String ACCEPTED_CLAZZ = "success";
    public static final String REJECTED_NAME = "Hủy đơn hàng";
    public static final String REJECTED_CLAZZ = "danger";

    private OrderUtils() {
    }

    /**
     * @param statusStr       trạng thái hiện tại
     * @param createdDate     thời gian tạo trạng thái hiện tại
     * @param combinationName tên người tạo ra trạng thái = firstName + lastName
     * @param roleStr         role người tạo ra trạng thái
     * @return vd: Đơn hàng đã được chấp nhận bởi Admin lúc 10:22:23 12/02/2022
     */
    public static String getStatusCombination(String statusStr, Date createdDate, String roleStr, String combinationName) {
        String result = "";
        StatusEnum status = StatusEnum.from(statusStr);
        String dateFmt = DateUtils.toStr(createdDate, DateUtils.F_DDMMYYYYHHMM);
        RoleEnum role = RoleEnum.valueOf(roleStr);

        switch (status) {
            case PENDING:
                result = "Đang chờ xứ lý";
                break;
            case CANCELED:
                result = "Đã bị hủy bới khách hàng vào lúc " + dateFmt;
                break;
            case REJECTED:
                result = "Đã bị từ chối bởi " + role.getName() + " " + combinationName + " vào lúc " + dateFmt;
                break;
            case ACCEPTED:
                result = "Được chấp nhận bởi " + role.getName() + " " + combinationName + " vào lúc " + dateFmt;
                break;
            default:
                break;
        }


        return result;
    }

    /**
     * @param shopPrice  Tổng tiền sản phẩm chưa trừ đi khuyến mãi sản phẩm.
     * @param shipPrice  Tiền ship
     * @param promotions Danh sách khuyến mãi
     * @return giá phải trả
     * @apiNote Dựa vào loại khuyến mãi và % giảm giá sẽ trừ đi vào giá
     */
    public static Long getTotal(Long shopPrice, Long shipPrice, List<PromotionDto> promotions) {
        try {
            /**
             * Nếu không có khuyến mãi thì = defaultTotal + shipPrice
             * */
            if (!promotions.isEmpty()) {
                for (PromotionDto promotion : promotions) {
                    PromotionTypeEnum type = PromotionTypeEnum.valueOf(promotion.getTypeCode());
                    switch (type) {
                        /**
                         * Loại 1: SHIP
                         * Trừ tiền ship
                         * */
                        case TYPE1:
                            shipPrice -= shipPrice * promotion.getPercentDiscount().longValue() / 100;
                            break;
                        /**
                         * Loại 2: Mua sắm
                         * Trừ tiền sản phẩm
                         * */
                        case TYPE2:
                            shopPrice -= shopPrice * promotion.getPercentDiscount().longValue() / 100;
                            break;
                        default:
                            throw new WsException(WsCode.INTERNAL_SERVER);
                    }
                }
            }
        } catch (Exception e) {
            log.error("getTotal: {}", e.getMessage());
        }
        return shopPrice + shipPrice;
    }

    public static ResultDto getResultDto(long shopPrice, Long shipPrice, List<PromotionDto> promotions) {
        Long shipDiscount = 0L;
        Long shopDiscount = 0l;

        if (!promotions.isEmpty()) {
            for (PromotionDto promotion : promotions) {
                PromotionTypeEnum type = PromotionTypeEnum.valueOf(promotion.getTypeCode());
                switch (type) {
                    case TYPE1:
                        shipDiscount += shipPrice * promotion.getPercentDiscount().longValue() / 100;
                        break;
                    case TYPE2:
                        shopDiscount += shopPrice * promotion.getPercentDiscount().longValue() / 100;
                        break;
                    default:
                        throw new WsException(WsCode.INTERNAL_SERVER);
                }

            }
        }

        Long shipTotal = shipPrice - shipDiscount;
        Long shopTotal = shopPrice - shopDiscount;

        Long total = shipTotal + shopTotal;

        return ResultDto.builder()
                .ship(PriceDto.builder()
                        .name("Vận chuyển")
                        .price(MoneyUtils.formatV2(shipPrice))
                        .discount(MoneyUtils.formatV2(shipDiscount))
                        .total(MoneyUtils.formatV2(shipTotal))
                        .build())
                .shop(PriceDto.builder()
                        .name("Mua sắm")
                        .price(MoneyUtils.formatV2(shopPrice))
                        .discount(MoneyUtils.formatV2(shopDiscount))
                        .total(MoneyUtils.formatV2(shopTotal))
                        .build())
                .total(MoneyUtils.formatV2(total))
                .build();
    }

    public static List<OptionDto> getOptions4Admin(String statusNow) {
        List<OptionDto> result = new ArrayList<>();
        try {
            final StatusEnum status = StatusEnum.from(statusNow);
            switch (status) {
                case PENDING:

                    result.add(getOptionDto(StatusEnum.ACCEPTED));
                    result.add(getOptionDto(StatusEnum.REJECTED));
                    break;
                case ACCEPTED:
//                    result.add(getOptionDto(StatusEnum.REJECTED));
                    result.add(getOptionDto(StatusEnum.SHIPPING));
                    break;
                case SHIPPING:
                    result.add(getOptionDto(StatusEnum.RECEIVED));
                    break;
                case REJECTED:
                case CANCELED:
                case RECEIVED:
                default:
                    break;
            }
        } catch (Exception e) {
            log.error("getOptions error: {}", e.getMessage());
        }
        return result;
    }

    private static OptionDto getOptionDto(StatusEnum status) {
        return OptionDto.builder()
                .name(status.getName())
                .status(status.name())
                .clazz(status.getClazz())
                .build();
    }

    public static List<String> getHistory(List<StatusDto> orderStatusList) {
        List<String> result = new ArrayList<>();
        orderStatusList.forEach(obj -> {
            String item = DateUtils.toStr(obj.getCreatedDate(), DateUtils.F_DDMMYYYYHHMMSS) + ": ";
            String combination = obj.getRole().getName() + " " + obj.getFullName();
            StatusEnum statusEnum = StatusEnum.from(obj.getStatus());
            switch (statusEnum) {
                case CANCELED:
                case PENDING:
                case SHIPPING:
                case RECEIVED:
                    item += statusEnum.getName();
                    break;
                case ACCEPTED:
                case REJECTED:
                    item += statusEnum.getName() + " bởi " + combination;
                    break;
                default:
                    throw new WsException(WsCode.INTERNAL_SERVER);
            }
            result.add(item);
        });
        return result;
    }

    public static String getStatusCombination(StatusEnum statusEnum, Date updatedDate, UserEntity lastModifiedUser) {
        if (null == statusEnum || updatedDate == null || lastModifiedUser == null || null == lastModifiedUser.getRole()) return null;
        RoleEnum roleEnum = lastModifiedUser.getRole();
        String roleName = roleEnum.getName();
        String dateFmt = DateUtils.toStr(updatedDate, DateUtils.F_DDMMYYYYHHMM);
        String combinationName = lastModifiedUser.getFirstName() + " " + lastModifiedUser.getLastName();
        switch (statusEnum) {
            case PENDING:
                return statusEnum.getName();
            case SHIPPING:
            case RECEIVED:
            case CANCELED:
                return dateFmt + " - " + statusEnum.getName();
            case REJECTED:
                return dateFmt + " - Bị từ chối bởi (" + roleName + ") " + combinationName;
            case ACCEPTED:
                return dateFmt + " - Được chấp nhận bởi (" + roleName + ") " + combinationName;
            default:
                break;
        }
        return null;
    }
}
