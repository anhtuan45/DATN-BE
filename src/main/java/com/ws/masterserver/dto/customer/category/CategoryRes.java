package com.ws.masterserver.dto.customer.category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryRes {
    private String categoryId;
    private String categoryName;
    private String slug;
}
