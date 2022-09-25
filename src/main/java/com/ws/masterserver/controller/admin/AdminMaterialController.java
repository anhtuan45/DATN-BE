package com.ws.masterserver.controller.admin;

import com.ws.masterserver.utils.base.WsController;
import com.ws.masterserver.utils.base.enum_dto.MaterialDto;
import com.ws.masterserver.utils.base.rest.ResData;
import com.ws.masterserver.utils.common.JsonUtils;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/material")
@RequiredArgsConstructor
@Slf4j
public class AdminMaterialController extends WsController {

    @PostMapping("/create")
    @Operation(summary = "API thêm mới chất liệu sp")
    public ResponseEntity<ResData<String>> create(@RequestBody MaterialDto dto){
        log.info("start api create with dto: {}", JsonUtils.toJson(dto));
        return ResponseEntity.status(HttpStatus.OK).body(service.materialService.create(getCurrentUser(), dto));
    }

    @PostMapping("/update")
    @Operation(summary = "API sửa chất liệu sp")
    public ResponseEntity<ResData<String>> update(@RequestBody MaterialDto dto){
        log.info("start api update with dto: {}", JsonUtils.toJson(dto));
        return ResponseEntity.status(HttpStatus.OK).body(service.materialService.update(getCurrentUser(), dto));
    }

    @PostMapping("/delete")
    @Operation(summary = "API xóa chất liệu sp")
    public ResponseEntity<ResData<String>> delete(@RequestBody MaterialDto dto){
        log.info("start api delele with dto: {}", JsonUtils.toJson(dto));
        return ResponseEntity.status(HttpStatus.OK).body(service.materialService.delete(getCurrentUser(), dto));
    }
}
