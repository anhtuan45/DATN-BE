package com.ws.masterserver.controller.admin;

import com.ws.masterserver.dto.admin.size.SizeResponseV2;
import com.ws.masterserver.dto.admin.size.create_update.SizeDto;
import com.ws.masterserver.dto.admin.size.search.SizeReq;
import com.ws.masterserver.utils.base.WsController;
import com.ws.masterserver.utils.base.rest.ResData;
import com.ws.masterserver.utils.common.JsonUtils;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class AdminSizeController extends WsController {
    @Operation(summary = "API lấy tất cả Size")
    @PostMapping("/v1/admin/size/search")
    public Object search(@RequestBody SizeReq payload) {
        return service.adminSizeDetailService.search(getCurrentUser(), payload);
    }
    @PostMapping("/v1/admin/size/create")
    @Operation(summary = "API thêm mới size")
    public Object create(@RequestBody SizeDto payload) {
        return service.adminSizeService.create(getCurrentUser(), payload);
    }

    @PostMapping("/v1/admin/size/update")
    public Object update(@RequestBody SizeDto payload) {
        return service.adminSizeService.update(getCurrentUser(), payload);
    }

    @DeleteMapping("/v1/admin/size/delete")
    @Operation(summary = "API xóa size")
    public Object delete(String id){
        return service.adminSizeService.delete(getCurrentUser(), id);
    }

    @GetMapping("/v1/admin/size/change-status")
    @Operation(summary = "API đổi trạng thái size")
    public Object changeStatus(String id){
        log.info("start api delete with dto: {}", JsonUtils.toJson(id));
        return service.sizeService.changeStatus(getCurrentUser(), id);
    }

    @GetMapping("/v1/admin/size/detail")
    @Operation(summary = "API admin chi tiết size sắc")
    public Object detail(String id) {
        log.info("start api detail with dto: {}", id);
        return service.sizeService.detail(getCurrentUser(), id);
    }
}
