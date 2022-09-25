package com.ws.masterserver.dto.admin.category;

import com.ws.masterserver.utils.base.rest.PageReq;
import lombok.Data;

@Data
public class CategoryReq {
    private String id;
    private Boolean status;
    private String textSearch;
    private String typeId;
    private PageReq pageReq;

}
