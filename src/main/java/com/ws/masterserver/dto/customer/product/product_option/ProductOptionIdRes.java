package com.ws.masterserver.dto.customer.product.product_option;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductOptionIdRes {
    private String productOptionId;
    private Long quantity;
    private Long price;
}
