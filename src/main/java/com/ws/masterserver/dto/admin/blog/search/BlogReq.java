package com.ws.masterserver.dto.admin.blog.search;

import com.ws.masterserver.utils.base.rest.PageReq;
import lombok.Data;

@Data
public class BlogReq {
    private String id;
    private Boolean active;
    private String textSearch;
    private String topicId;
    private PageReq pageReq;
}
