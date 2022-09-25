package com.ws.masterserver.dto.customer.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ColorResponse {
    private String colorId;
    private String colorName;
}
