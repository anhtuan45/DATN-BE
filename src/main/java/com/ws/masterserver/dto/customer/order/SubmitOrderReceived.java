package com.ws.masterserver.dto.customer.order;

import lombok.Data;

@Data
public class SubmitOrderReceived {
    private String orderId;
    private String note;
}
