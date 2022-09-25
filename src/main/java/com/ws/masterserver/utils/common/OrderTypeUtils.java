package com.ws.masterserver.utils.common;

import com.ws.masterserver.dto.admin.order.search.TypeDto;
import com.ws.masterserver.utils.constants.enums.OrderTypeEnum;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OrderTypeUtils {
    public static TypeDto getOrderTypeDto(String input) {
        try {
            OrderTypeEnum orderType = OrderTypeEnum.valueOf(input);
            return TypeDto.builder()
                    .code(orderType.name())
                    .title(orderType.getTitle())
                    .name(orderType.getName())
                    .build();
        } catch (Exception e) {
            return new TypeDto();
        }
    }

    public static String getOrderTypeStr(String input, Boolean payed) {
        try {
            OrderTypeEnum orderType = OrderTypeEnum.valueOf(input);

            return TypeDto.builder()
                    .code(orderType.name())
                    .title(orderType.getTitle())
                    .name(orderType.getName())
                    .build().getName()  + (payed ? "(Đã thanh toán)" : "(Chưa thanh toán)");
        } catch (Exception e) {
            log.error("OrderTypeUtils getOrderTypeStr error: {}", e.getMessage());
            return "";
        }
    }
}
