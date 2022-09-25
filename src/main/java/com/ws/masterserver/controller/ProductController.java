package com.ws.masterserver.controller;

import com.ws.masterserver.dto.customer.product.ProductRelatedReq;
import com.ws.masterserver.dto.customer.product.ProductReq;
import com.ws.masterserver.dto.admin.product.ProductDetailResponse;
import com.ws.masterserver.utils.base.WsController;
import com.ws.masterserver.utils.base.rest.ResData;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
@Slf4j
public class ProductController extends WsController {

    @PostMapping("/search")
    public ResponseEntity<?> search(@RequestBody ProductReq req){
        return ResponseEntity.status(HttpStatus.OK).body(service.productService.search( req));
    }

    @Operation(summary = "API GET Product Detail")
    @GetMapping("/{id}")
    public ResponseEntity<ResData<ProductDetailResponse>> getProductDetail(@PathVariable("id") String id){
        return ResponseEntity.status(HttpStatus.OK).body(service.productService.getProductDetail(id));
    }

    @PostMapping("/search/v2")
    public ResponseEntity<?> searchV2(@RequestBody com.ws.masterserver.dto.customer.product.search.ProductReq req){
        return ResponseEntity.ok(service.productService.searchV2(req));
    }

    @Operation(summary = "API GET Related Product")
    @PostMapping("/relatedProduct")
    public ResponseEntity<?> getRelatedProduct(@RequestBody ProductRelatedReq req){
        return ResponseEntity.ok(service.productService.getRelatedProduct(req.getProductId()));
    }

    @Operation(summary = "product no-page")
    @GetMapping("/no-page")
    public ResponseEntity<Object> productNoPage() {
        return ResponseEntity.ok(service.productInfoService.noPage(getCurrentUser()));
    }

    @Operation(summary = "product best seller")
    @GetMapping("/best-seller")
    public ResponseEntity<?> productBestSeller() {
        return ResponseEntity.ok(service.productService.getProductBestSeller());
    }
}
