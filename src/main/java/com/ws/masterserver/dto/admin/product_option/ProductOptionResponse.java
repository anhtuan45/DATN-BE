package com.ws.masterserver.dto.admin.product_option;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductOptionResponse {
    private String id;
    private String sizeId;
    private String sizeName;
    private String colorName;
    private Long qty;
    private String price;
    private String image;
}