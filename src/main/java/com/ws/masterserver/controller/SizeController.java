package com.ws.masterserver.controller;

import com.ws.masterserver.utils.base.WsController;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/size")
@RequiredArgsConstructor
@Slf4j
public class SizeController extends WsController {

    @Operation(summary = "API lấy tất cả size")
    @GetMapping("")
    public ResponseEntity<?> getAllColor() {
        log.info("START API /api/v1/size");
        return ResponseEntity.status(HttpStatus.OK).body(service.sizeService.getAllSize());
    }

}
