package com.ws.masterserver.dto.customer.review.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewRequest {
    private String content;
    private Integer rating;
    private String productId;
    private String orderId;
}
