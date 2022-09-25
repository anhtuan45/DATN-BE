package com.ws.masterserver.dto.admin.product.detail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OptionDto {
    private String id;
    private String colorId;
    private String sizeId;
    private String image;
    private Long price;
    private Long qty;
}
