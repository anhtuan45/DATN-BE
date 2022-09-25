package com.ws.masterserver.dto.customer.review.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewResponse {
    private String reviewId;
    private Boolean active;
    private String content;
    //sao đánh giá (từ 1 -> 5)
    private Integer rating;
    private String userName;
    private String createdDate;
}
