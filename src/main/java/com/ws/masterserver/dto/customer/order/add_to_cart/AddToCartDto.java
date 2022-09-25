package com.ws.masterserver.dto.customer.order.add_to_cart;

import lombok.Data;

@Data
public class AddToCartDto {
    /** productId */
    private String id;

    /** product price */
    private Long price;

    /** qty */
    private Long qty;


}
