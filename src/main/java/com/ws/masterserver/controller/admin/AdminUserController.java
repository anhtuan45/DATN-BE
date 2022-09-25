package com.ws.masterserver.controller.admin;

import com.ws.masterserver.dto.admin.user.info.UserDto;
import com.ws.masterserver.dto.admin.user.search.UserReq;
import com.ws.masterserver.utils.base.WsController;
import com.ws.masterserver.utils.common.JsonUtils;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class AdminUserController extends WsController {
    @Operation(summary = "Danh sách tai khoan")
    @PostMapping("/v1/admin/user/search")
    public Object search(@RequestBody UserReq req) {
        log.info("start api /api/v1/admin/user/search with req: {}", JsonUtils.toJson(req));
        return service.adminUserService.search(getCurrentUser(), req);
    }

    @DeleteMapping("/v1/admin/user/delete")
    public Object delete(String id) {
        log.info("start api /api/v1/admin/user/delete with id: {}", id);
        return service.adminUserInfoService.delete(getCurrentUser(), id);
    }

    @Operation(summary = "Chi tiết tài khoan")
    @GetMapping("/v1/admin/user/detail")
    public ResponseEntity<Object> detail(String id) {
        log.info("start api /api/v1/admin/user/detail with id: {}", id);
        return ResponseEntity.ok(service.adminUserService.detail(getCurrentUser(), id));
    }

    @Operation(summary = "them moi tai khoan")
    @PostMapping("/v1/admin/user/create")
    public ResponseEntity<Object> create(@RequestBody UserDto dto) {
        log.info("start api /api/v1/admin/user/create with dto: {}", JsonUtils.toJson(dto));
        return ResponseEntity.ok(service.adminUserInfoService.create(getCurrentUser(), dto));
    }

    @Operation(summary = "cap nhat tai khoan")
    @PostMapping("/v1/admin/user/update")
    public ResponseEntity<Object> update(@RequestBody UserDto dto) {
        log.info("start api /api/v1/admin/user/update with dto: {}", JsonUtils.toJson(dto));
        return ResponseEntity.ok(service.adminUserInfoService.update(getCurrentUser(), dto));
    }

    @GetMapping("/v1/admin/user/change-status")
    @Operation(summary = "Thay đổi trang thái user từ active-deactive và ngược lại\n\n. " +
            "role admin chỉ được thay đổi của role staff")
    public ResponseEntity<Object> changeStatus(String id) {
        log.info("start api /api/v1/admin/user/change-status with req: {}", id);
        return ResponseEntity.ok(service.adminUserInfoService.changeStatus(getCurrentUser(), id));
    }
}
