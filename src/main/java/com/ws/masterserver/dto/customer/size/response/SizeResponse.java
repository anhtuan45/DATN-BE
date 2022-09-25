package com.ws.masterserver.dto.customer.size.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SizeResponse {
    private String sizeId;
    private String sizeName;
}
