package com.ws.masterserver.dto.customer.suggest;

import com.ws.masterserver.utils.constants.enums.BodyFigureTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SuggestDto {
    /**
     * loại sản phẩm
     */
    private String categoryId;

    /**
     * chiều cao
     */
    private String height;

    /**
     * cân nặng
     */
    private String weight;

    /**
     * tạng người
     */
    private BodyFigureTypeEnum type;
}
