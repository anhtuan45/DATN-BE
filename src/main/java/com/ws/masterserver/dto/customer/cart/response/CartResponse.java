package com.ws.masterserver.dto.customer.cart.response;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartResponse {
    private String cartId;
    private String productId;
    private String productOptionId;
    private String productName;
    private String sizeName;
    private String colorName;
    private Integer quantity;
    private Long quantityAvailable;
    private Long price;
    private String image;
    private Long subtotal;
    private String categoryId;
    private Long discount;

    public CartResponse(String cartId, String productId, String productOptionId, String productName, String sizeName, String colorName, Integer quantity, Long quantityAvailable, Long price, String image, Long subtotal, String categoryId) {
        this.cartId = cartId;
        this.productId = productId;
        this.productOptionId = productOptionId;
        this.productName = productName;
        this.sizeName = sizeName;
        this.colorName = colorName;
        this.quantity = quantity;
        this.quantityAvailable = quantityAvailable;
        this.price = price;
        this.image = image;
        this.subtotal = subtotal;
        this.categoryId = categoryId;
    }
}
