package com.ws.masterserver.controller;

import com.ws.masterserver.utils.base.WsController;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/customer-type")
@RequiredArgsConstructor
@Slf4j
public class CustomerTypeController extends WsController {
    @Operation(summary = "customer type no-page")
    @GetMapping("/no-page")
    public ResponseEntity<Object> NoPage() {
        return ResponseEntity.ok(service.customerTypeInfoService.noPage(getCurrentUser()));
    }
}
