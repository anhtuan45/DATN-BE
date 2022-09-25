package com.ws.masterserver.controller.admin;

import com.ws.masterserver.dto.admin.category.CategoryDto;
import com.ws.masterserver.dto.admin.category.CategoryReq;
import com.ws.masterserver.dto.admin.category.CategoryRes;
import com.ws.masterserver.utils.base.WsController;
import com.ws.masterserver.utils.base.rest.PageData;
import com.ws.masterserver.utils.base.rest.ResData;
import com.ws.masterserver.utils.common.JsonUtils;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/category")
@RequiredArgsConstructor
@Slf4j
public class AdminCategoryController extends WsController {

    @PostMapping("/search")
    @Operation(summary = "API tìm kiếm danh sách danh muc sp")
    public ResponseEntity<PageData<CategoryRes>> search(@RequestBody CategoryReq req) {
        log.info("start api search with req: {}", JsonUtils.toJson(req));
        return ResponseEntity.ok(service.categoryService.search(getCurrentUser(), req));
    }

    @PostMapping("/create")
    @Operation(summary = "API thêm mới danh muc sp")
    public ResponseEntity<ResData<String>> create(@RequestBody CategoryDto dto) {
        log.info("start api create with dto: {}", JsonUtils.toJson(dto));
        return ResponseEntity.status(HttpStatus.OK).body(service.categoryService.create(getCurrentUser(), dto));
    }

    @DeleteMapping("/delete")
    @Operation(summary = "API xóa danh muc sản phẩm")
    public ResponseEntity<ResData<String>> delete(String id) {
        log.info("start api delete with dto: {}", id);
        return ResponseEntity.status(HttpStatus.OK).body(service.categoryService.delete(getCurrentUser(), id));
    }

    @PostMapping("/update")
    @Operation(summary = "API update danh muc sản phẩm")
    public Object update(@RequestBody CategoryDto dto) {
        log.info("start api update with dto: {}", JsonUtils.toJson(dto));
        return service.categoryService.update(getCurrentUser(), dto);
    }

    @GetMapping("/detail")
    @Operation(summary = "API chi tiết danh muc sản phẩm")
    public Object detail(String id) {
        log.info("start api detail with dto: {}", id);
        return service.adminCategoryDetailService.detail(getCurrentUser(), id);
    }

    @GetMapping("/change-status")
    @Operation(summary = "API thay doi trang thai danh muc sản phẩm")
    public ResponseEntity<Object> changeStatus(String id) {
        log.info("start api detail with payload: {}", id);
        return ResponseEntity.ok(service.categoryService.changeStatus(getCurrentUser(), id));
    }
}
