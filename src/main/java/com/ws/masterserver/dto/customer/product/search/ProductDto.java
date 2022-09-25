package com.ws.masterserver.dto.customer.product.search;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class ProductDto {
    /**
     * productId
     */
    private String id;

    /**
     * tên sản phẩm
     */
    private String name;

    /**
     * Giá: giá hiển thị(Lấy giá thấp nhất trong danh sách productOption
     */
    private Long price;

    private Long minPrice;

    private Long maxPrice;

    /**
     * Size
     */
    private List<String> sizes;

    /**
     * màu sắc
     */
    private List<String> colors;

    /**
     * Ảnh hiển thị
     */
    private List<String> images;

    /**
     * tên loại sản phẩm
     */
    private String categoryName;

    /**
     * tên chất liệu
     */
    private String materialName;

    /**
     * mô tả
     */
    private String des;

    /**
     * giới tính
     */
    private String typeName;

    private Date createdDate;
}
