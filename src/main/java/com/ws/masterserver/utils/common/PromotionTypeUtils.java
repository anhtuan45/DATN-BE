package com.ws.masterserver.utils.common;

import com.ws.masterserver.utils.constants.enums.PromotionTypeEnum;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PromotionTypeUtils {

    public static String getName(String code) {
        try {
            PromotionTypeEnum promotionTypeEnum = PromotionTypeEnum.valueOf(PromotionTypeEnum.class, code);
            return promotionTypeEnum.getName();
        } catch (Exception e) {
            log.error("getName: {}", e.getMessage());
            return "";
        }
    }

}
