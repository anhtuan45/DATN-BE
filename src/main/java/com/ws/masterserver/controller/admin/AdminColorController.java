package com.ws.masterserver.controller.admin;

import com.ws.masterserver.dto.admin.color.search.ColorReq;
import com.ws.masterserver.utils.base.WsController;
import com.ws.masterserver.utils.base.enum_dto.ColorDto;
import com.ws.masterserver.utils.base.rest.ResData;
import com.ws.masterserver.utils.common.JsonUtils;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/color")
@RequiredArgsConstructor
@Slf4j
public class AdminColorController extends WsController {

    @PostMapping("/create")
    @Operation(summary = "API thêm mới màu sp")
    public ResponseEntity<ResData<String>> create(@RequestBody ColorDto dto) {
        log.info("start api create with dto: {}", JsonUtils.toJson(dto));
        return ResponseEntity.status(HttpStatus.OK).body(service.colorService.create(getCurrentUser(), dto));
    }

    @PostMapping("/update")
    @Operation(summary = "API cập nhật màu sp")
    public ResponseEntity<ResData<String>> update(@RequestBody ColorDto dto){
        log.info("start api update with dto: {}", JsonUtils.toJson(dto));
        return ResponseEntity.status(HttpStatus.OK).body(service.colorService.update(getCurrentUser(), dto));
    }

    @PostMapping("/delete")
    @Operation(summary = "API xóa màu sp")
    public ResponseEntity<ResData<String>> delete(String id){
        log.info("start api delete with dto: {}", JsonUtils.toJson(id));
        return ResponseEntity.status(HttpStatus.OK).body(service.colorService.delete(getCurrentUser(), id));
    }

    @PostMapping("/change-status")
    @Operation(summary = "API đổi trạng thái màu sp")
    public ResponseEntity<ResData<String>> changeStatus(String id){
        log.info("start api delete with dto: {}", JsonUtils.toJson(id));
        return ResponseEntity.status(HttpStatus.OK).body(service.colorService.changeStatus(getCurrentUser(), id));
    }
    @Operation(summary = "API lấy tất cả màu sắc V2")
    @GetMapping("")
    public ResponseEntity<?> getAllColorV2() {
        log.info("START API /api/v1/admin/color");
        return ResponseEntity.status(HttpStatus.OK).body(service.colorService.getListColorV2());
    }

    @GetMapping("/detail/{id}")
    @Operation(summary = "API admin chi tiết màu sắc")
    public Object detail(@PathVariable("id") String id) {
        log.info("start api detail with dto: {}", id);
        return service.colorService.detail(getCurrentUser(), id);
    }

    @PostMapping("/search")
    @Operation(summary = "tim kiem")
    public ResponseEntity<Object> search(@RequestBody ColorReq payload) {
        log.info("start api /api/v1/admin/color/search with payload: {}", JsonUtils.toJson(payload));
        return ResponseEntity.ok(service.colorService.search(getCurrentUser(), payload));
    }
}
