package com.ws.masterserver.controller.admin;

import com.ws.masterserver.dto.admin.customer.search.CustomerReq;
import com.ws.masterserver.utils.base.WsController;
import com.ws.masterserver.utils.common.JsonUtils;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/customer")
@RequiredArgsConstructor
@Slf4j
public class AdminCustomerController extends WsController {

    @PostMapping("/search")
    @Operation(summary = "tim kiem")
    public ResponseEntity<Object> search(@RequestBody CustomerReq payload) {
        log.info("start api /api/v1/admin/customer/search with payload: {}", JsonUtils.toJson(payload));
        return ResponseEntity.ok(service.adminCustomerDetailService.search(getCurrentUser(), payload));
    }

}
