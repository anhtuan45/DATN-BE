package com.ws.masterserver.dto.customer.blog;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BlogResponse {
    private String id;

    private String content;

    private Date createdDate;

    private String title;

    private String topicId;

    private String image;

    private String description;

    private String name;

    private Boolean active;

}