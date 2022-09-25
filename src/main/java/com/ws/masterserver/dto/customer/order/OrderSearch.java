package com.ws.masterserver.dto.customer.order;

import com.ws.masterserver.utils.base.rest.PageReq;
import com.ws.masterserver.utils.constants.enums.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderSearch {
    private String textSearch;
    private String status;
    private PageReq pageReq;
}
