package com.ws.masterserver.dto.admin.product.detail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetailRes {
    private Object id;
    private Object name;
    private Object categoryId;
    private Object materialId;
    private Object des;
    private Object options;
    private Object reviews;
}
