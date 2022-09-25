package com.ws.masterserver.dto.admin.product.create_update;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OptionDto {
    private String id;
    private String colorId;
    private String price;
    private String qty;
    private String sizeId;
    private String image;
}
