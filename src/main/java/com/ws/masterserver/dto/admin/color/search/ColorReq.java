package com.ws.masterserver.dto.admin.color.search;

import com.ws.masterserver.utils.base.rest.PageReq;
import lombok.Data;

@Data
public class ColorReq {
    private String id;
    private Boolean active;
    private String textSearch;
    private PageReq pageReq;
}
