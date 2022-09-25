package com.ws.masterserver.dto.customer.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductInOrderDetail {
    private String productName;
    private String productOptionId;
    private String productId;
    private String image;
    private String sizeName;
    private String colorName;
    private String reviewId;
    private Long price;
    private Integer qty;
    private Long subtotal;
}
