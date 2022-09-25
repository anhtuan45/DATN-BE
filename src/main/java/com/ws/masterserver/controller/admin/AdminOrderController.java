package com.ws.masterserver.controller.admin;

import com.ws.masterserver.dto.admin.order.change_status.ChangeStatusDto;
import com.ws.masterserver.dto.admin.order.search.OrderReq;
import com.ws.masterserver.utils.base.WsController;
import com.ws.masterserver.utils.common.JsonUtils;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/order")
@RequiredArgsConstructor
@Slf4j
public class AdminOrderController extends WsController {

    @PostMapping("/search")
    @Operation(summary = "API danh sách hóa đơn")
    public ResponseEntity<Object> search4Admin(@RequestBody OrderReq req) {
        return ResponseEntity.ok(service.adminOrderDetailService.search(getCurrentUser(), req));
    }

    @GetMapping("/detail")
    @Operation(summary = "API chi tiet hoa don")
    public Object detail4Admin(String id) {
        return service.adminOrderDetailService.detail(getCurrentUser(), id);
    }

    @PostMapping("/change-status")
    @Operation(summary = "API thay đổi trạng thái đơn hàng")
    public Object changeStatus4Admin(@RequestBody ChangeStatusDto payload) {
        return service.adminOrderService.changeStatus(getCurrentUser(), payload);
    }
}
