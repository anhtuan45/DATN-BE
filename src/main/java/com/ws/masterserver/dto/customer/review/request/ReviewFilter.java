package com.ws.masterserver.dto.customer.review.request;

import com.ws.masterserver.utils.base.rest.PageReq;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewFilter {
    private Integer rate;
    private String productId;
    private PageReq pageReq;
}
