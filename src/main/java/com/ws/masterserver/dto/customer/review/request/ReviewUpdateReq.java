package com.ws.masterserver.dto.customer.review.request;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewUpdateReq {
    private String id;
    private String content;
    private Integer rating;
}
