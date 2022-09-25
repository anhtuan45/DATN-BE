package com.ws.masterserver.dto.customer.product.product_option;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

@Data
public class ProductOptionIdReq {
    @NotNull
    private String sizeId;
    @NotNull
    private String colorId;
    @NotNull
    private String productId;
}
