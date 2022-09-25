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
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class CustomerController extends WsController {

    @Operation(summary = "customer no-page")
    @GetMapping("/v1/customer/no-page")
    public ResponseEntity<Object> customerNoPage() {
        return ResponseEntity.ok(service.customerInfoService.noPage(getCurrentUser()));
    }


}
