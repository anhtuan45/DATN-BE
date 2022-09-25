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
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
@Slf4j
public class CategoryController extends WsController {

    @Operation(summary = "category no-page")
    @GetMapping("/no-page")
    public ResponseEntity<Object> categoryNoPage() {
        return ResponseEntity.ok(service.categoryInfoService.noPage(getCurrentUser()));
    }
}
