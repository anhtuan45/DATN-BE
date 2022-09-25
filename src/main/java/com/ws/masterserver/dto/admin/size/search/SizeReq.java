package com.ws.masterserver.dto.admin.size.search;

import com.ws.masterserver.utils.base.rest.PageReq;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SizeReq {
    private String textSearch;
    private Boolean active;
    private PageReq pageReq;
}
