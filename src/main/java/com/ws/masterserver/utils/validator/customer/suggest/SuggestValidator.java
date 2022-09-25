package com.ws.masterserver.utils.validator.customer.suggest;

import com.ws.masterserver.dto.customer.suggest.SuggestDto;
import com.ws.masterserver.utils.base.WsException;
import com.ws.masterserver.utils.common.StringUtils;
import com.ws.masterserver.utils.constants.WsCode;

/**
 * @author myname
 */
public class SuggestValidator {
    public static void validGetSizeAvailable(SuggestDto dto) {
        if (StringUtils.isNullOrEmpty(dto.getCategoryId())) {
            throw new WsException(WsCode.BAD_REQUEST, "Không được để trống loại sản phẩm");
        }
        if (StringUtils.isNullOrEmpty(dto.getWeight())) {
            throw new WsException(WsCode.BAD_REQUEST, "Không được để trống cân nặng");
        }
        if (StringUtils.isNullOrEmpty(dto.getHeight())) {
            throw new WsException(WsCode.BAD_REQUEST, "Không được để trống chiều cao");
        }
        try {
            Long weight = Long.valueOf(dto.getWeight());
            if (weight < 41 || weight > 105) {
                throw new WsException(WsCode.BAD_REQUEST, "Cân nặng không hợp lệ");
            }
        } catch (Exception e) {
            throw new WsException(WsCode.BAD_REQUEST, "Cân nặng không hợp lệ");
        }
        try {
            Long height = Long.valueOf(dto.getHeight());
            if (height < 149 || height > 195) {
                throw new WsException(WsCode.BAD_REQUEST, "Chiều cao không hợp lệ");
            }
        } catch (Exception e) {
            throw new WsException(WsCode.BAD_REQUEST, "Chiều cao không hợp lệ");
        }
    }
}
