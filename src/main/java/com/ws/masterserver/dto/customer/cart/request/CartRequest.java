package com.ws.masterserver.dto.customer.cart.request;

import lombok.*;
import org.jetbrains.annotations.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartRequest {
    @NotNull
    private String productOptionId;
    @NotNull
    private Integer quantity;
}
