package com.ws.masterserver.controller;

import com.ws.masterserver.dto.customer.order.me.MyOrderRequest;
import com.ws.masterserver.dto.customer.order.me.MyOrderResponse;
import com.ws.masterserver.utils.base.WsController;
import com.ws.masterserver.utils.base.rest.PageData;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/color")
@RequiredArgsConstructor
@Slf4j
public class ColorController extends WsController {

    @Operation(summary = "API lấy tất cả màu sắc")
    @GetMapping("")
    public ResponseEntity<?> getAllColor() {
        log.info("START API /api/v1/color");
        return ResponseEntity.status(HttpStatus.OK).body(service.colorService.getListColor());
    }
}
