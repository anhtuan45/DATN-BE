package com.ws.masterserver.dto.admin.order.detail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Class đang map tới method findByOrderId trong OrderPromotionRepository
 * Nếu move class này thì cần copy đường dẫn lại trong câu jpql trong OrderPromotionRepository
 * */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PromotionDto {
    private String voucher;
    private Double percentDiscount;
    private String name;
    private String typeCode;

    public PromotionDto(String voucher, Double percentDiscount, String name, String typeCode) {
        this.voucher = voucher;
        this.percentDiscount = percentDiscount;
        this.name = name;
        this.typeCode = typeCode;
    }

    private String typeName;
}
