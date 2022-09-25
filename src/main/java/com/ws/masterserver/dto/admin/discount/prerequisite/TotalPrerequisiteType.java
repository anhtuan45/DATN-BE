package com.ws.masterserver.dto.admin.discount.prerequisite;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;

@JsonTypeName("total")
@Data
public class TotalPrerequisiteType extends PrerequisiteDto{
    /**
     * Tổng giá trị đơn hàng tối thiêu
     */
    private String minimumSaleTotalPrice;
}