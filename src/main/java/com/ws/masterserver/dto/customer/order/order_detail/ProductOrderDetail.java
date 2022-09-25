package com.ws.masterserver.dto.customer.order.order_detail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductOrderDetail {
    private String productOptionId;
    private Integer quantity;
}
