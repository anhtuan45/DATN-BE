package com.ws.masterserver.dto.customer.cart.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ListCartResponse {
    private List<CartResponse> carts;
    private Long totalPrice;
    private Integer totalItems;
}
