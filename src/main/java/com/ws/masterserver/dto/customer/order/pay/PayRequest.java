package com.ws.masterserver.dto.customer.order.pay;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PayRequest {
    private Long amount;
    private String description;
    private String bankCode;
}