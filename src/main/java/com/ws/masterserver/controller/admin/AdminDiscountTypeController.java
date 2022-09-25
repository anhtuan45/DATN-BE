package com.ws.masterserver.controller.admin;

import com.ws.masterserver.utils.base.WsController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/discount-type")
@RequiredArgsConstructor
@Slf4j
public class AdminDiscountTypeController extends WsController {
    @GetMapping("/no-page")
    public ResponseEntity<Object> noPage() {
        return ResponseEntity.ok(service.adminDiscountTypeService.noPage(getCurrentUser()));
    }
}
