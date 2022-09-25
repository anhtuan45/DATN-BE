package com.ws.masterserver.controller.admin;

import com.ws.masterserver.dto.admin.product.create_update.ProductDto;
import com.ws.masterserver.dto.customer.product.ProductReq;
import com.ws.masterserver.utils.base.WsController;
import com.ws.masterserver.utils.common.JsonUtils;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/product")
@RequiredArgsConstructor
@Slf4j
public class AdminProductController extends WsController {

    @Operation(summary = "API search product cho admin")
    @PostMapping("/search")
    public Object search4Admin(@RequestBody ProductReq req) {
        log.info("start api /api/v1/product/admin/search with req: {}", JsonUtils.toJson(req));
        return service.adminProductDetailService.search(getCurrentUser(), req);
    }

    @Operation(summary = "API detail product cho admin")
    @GetMapping("/detail")
    public ResponseEntity<Object> detail(String id) {
        log.info("start api /api/v1/product/admin/detail with id: {}", id);
        return ResponseEntity.ok(service.adminProductDetailService.detail(getCurrentUser(), id));
    }

    @PostMapping("/create")
    public Object create(@RequestBody ProductDto dto) {
        return service.adminProductService.create(getCurrentUser(), dto);
    }

    @GetMapping("/change-status")
    public Object changeStatus(String id) {
        return service.adminProductService.changeStatus(getCurrentUser(), id);
    }

    @DeleteMapping("/delete")
    public Object delete(String id) {
        return service.adminProductService.delete(getCurrentUser(), id);
    }

    @DeleteMapping("/delete-option")
    public Object deleteOption(String productId, String optionId) {
        return service.adminProductService.deleteOption(getCurrentUser(), productId, optionId);
    }

    @PostMapping("/update")
    public Object update(@RequestBody ProductDto payload) {
        return service.adminProductService.update(getCurrentUser(), payload);
    }

    @Operation(summary = "product no-category")
    @GetMapping("/no-category")
    public ResponseEntity<Object> productNoCategory() {
        return ResponseEntity.ok(service.productInfoService.noCategory(getCurrentUser()));
    }

    @Operation(summary = "product no-category")
    @GetMapping("/no-category-update")
    public Object productNoCategoryUpdate(String id) {
        return service.productInfoService.noCategoryUpdate(getCurrentUser(), id);
    }
}
