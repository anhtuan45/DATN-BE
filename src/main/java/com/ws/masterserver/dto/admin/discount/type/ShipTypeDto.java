package com.ws.masterserver.dto.admin.discount.type;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;

import java.util.List;

@Data
@JsonTypeName("ship")
public class ShipTypeDto extends DiscountTypeDto {
    /**
     * Miễn phi tối đa
     */
    private String shipValueLimitAmount;
    /**
     * tỉnh/thành: all hoặc list danh sách tỉnh thành
     */
    private String provinceSelection;
    /**
     * Danh sách tỉnh thành được áp dụng
     */
    private List<String> provinceIds;

    /**
     * Có Áp dụng với phí vận chuyển dưới ${maximumShippingRate} hay không?
     */
//        private Boolean hasMaximumShippingRate;
    /**
     * Áp dụng với phí vận chuyển dưới
     */
    private String maximumShippingRate;
}
