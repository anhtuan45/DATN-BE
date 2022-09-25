package com.ws.masterserver.dto.customer.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse {
    private String id;
    private String name;
    private Boolean active;
    private String thumbnail;
    private String price;
    private String categoryName;
    private String des;
    private String materialName;
    private Date createdDate;
    private String createdBy;
    private String createdName;
}
