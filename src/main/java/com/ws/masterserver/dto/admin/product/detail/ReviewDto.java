package com.ws.masterserver.dto.admin.product.detail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDto {
    private String id;
    private String content;
    private Integer rating;
    private String userId;
    private String userFullName;
    private Date createdDate;
    private String createdDateFmt;

    public ReviewDto(String id, String content, Integer rating, String userId, String userFullName, Date createdDate) {
        this.id = id;
        this.content = content;
        this.rating = rating;
        this.userId = userId;
        this.userFullName = userFullName;
        this.createdDate = createdDate;
    }
}
