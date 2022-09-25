package com.ws.masterserver.controller.admin;

import com.ws.masterserver.dto.admin.order.search.OrderReq;
import com.ws.masterserver.utils.base.WsController;
import com.ws.masterserver.utils.common.JsonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v2/admin/order")
@RequiredArgsConstructor
@Slf4j
public class AdminOrderControllerV2 extends WsController {
    @PostMapping("/search")
    public ResponseEntity<Object> searchV2(@RequestBody OrderReq payload) {
        log.info("START API /api/v1/order/admin/search-v2 with dto: {}", JsonUtils.toJson(payload));
        return ResponseEntity.ok(service.adminOrderDetailServiceV2.search(getCurrentUser(), payload));
    }
}
