package com.ws.masterserver.dto.admin.category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRes {
    private String id;
    private String name;
    private Object des;
    private String image;
    private Boolean active;
    private String activeName;
    private Date createdDate;
    private String createdDateFmt;
    /**
     * Số lượng sản phẩm
     */
    private Long productNumber;
    private String activeClazz;
    private String typeName;
}
