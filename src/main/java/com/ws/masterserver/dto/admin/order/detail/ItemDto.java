package com.ws.masterserver.dto.admin.order.detail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemDto {
    //productId
    private String productId;
    private String productName;
    private String color;
    private String size;
    private String image;
    private String materialName;
    private String categoryName;
    private Integer qty;
    private Long price;
    private String priceFmt;
    private Long discount;
    private String discountFmt;
    private Long total;
    private String totalFmt;

    public ItemDto(String productId, String productName, String color, String size, String image, String materialName, String categoryName, Integer qty, Long price, Long discount, Long total) {
        this.productId = productId;
        this.productName = productName;
        this.color = color;
        this.size = size;
        this.image = image;
        this.materialName = materialName;
        this.categoryName = categoryName;
        this.qty = qty;
        this.price = price;
        this.discount = discount;
        this.total = total;
    }
}
