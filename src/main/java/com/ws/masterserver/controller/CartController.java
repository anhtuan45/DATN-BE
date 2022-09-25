package com.ws.masterserver.controller;

import com.ws.masterserver.dto.customer.cart.request.CartRequest;
import com.ws.masterserver.utils.base.WsController;
import com.ws.masterserver.utils.base.rest.ResData;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
@Slf4j
public class CartController extends WsController {

    @Operation(summary = "API thêm sản phẩm vào giỏ hàng")
    @PostMapping("/add-to-cart")
    public ResponseEntity<ResData<String>> addToCart(@RequestBody CartRequest cartRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(service.cartService.addToCart(getCurrentUser(), cartRequest));
    }

    @Operation(summary = "API hiển thị tất sản phẩm ở giỏ hàng")
    @GetMapping("/listCart")
    public ResponseEntity<?> getAllListCart(){
        return ResponseEntity.status(HttpStatus.OK).body(service.cartService.getListCart(getCurrentUser()));
    }

    @Operation(summary = "API cập nhật số lượng sản phẩm trong giỏ hàng")
    @PostMapping("/update-cart")
    public ResponseEntity<?> updateCart(@RequestBody CartRequest cartRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(service.cartService.updateCart(getCurrentUser(), cartRequest));
    }

    @Operation(summary = "API xóa sản phẩm trong giỏ hàng")
    @DeleteMapping("/delete/{productOptionId}")
    public ResponseEntity<?> deleteItemInCart(@PathVariable("productOptionId") String productOptionId) {
        return ResponseEntity.status(HttpStatus.OK).body(service.cartService.deleteItemInCart(getCurrentUser(), productOptionId));
    }

    @Operation(summary = "Đếm số lượng sản phẩm trong giỏ hàng")
    @GetMapping("/countCartItem")
    public ResponseEntity<?> countCartItem() {
        return ResponseEntity.status(HttpStatus.OK).body(service.cartService.countCartItem(getCurrentUser()));
    }


}
