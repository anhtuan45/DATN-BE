package com.ws.masterserver.controller;

import com.ws.masterserver.dto.customer.order.*;
import com.ws.masterserver.utils.base.WsController;
import com.ws.masterserver.utils.common.JsonUtils;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
@Slf4j
public class OrderController extends WsController {

    @Operation(summary = "API checkout with other address")
    @PostMapping("/checkout/other-address")
    public ResponseEntity<?> createOrder(@RequestBody OrderRequest req) {
        log.info("----- START API /api/v1/order/checkout ------");
        return ResponseEntity.ok(service.orderService.checkout2(getCurrentUser(), req));
    }

    @Operation(summary = "API checkout")
    @PostMapping("/checkout")
    public ResponseEntity<?> createOrder2(@RequestBody OrderReqq req) {
        log.info("----- START API /api/v1/order/checkin ------");
        return ResponseEntity.ok(service.orderService.checkout(getCurrentUser(), req));
    }


    @Operation(summary = "My Orders")
    @GetMapping("/myOrders")
    public ResponseEntity<?> getMyOrder() {
        log.info("START API /api/v1/order/myOrder");
        return ResponseEntity.ok(service.orderService.getMyOrders(getCurrentUser()));
    }

    @Operation(summary = "API count my order")
    @GetMapping("/count/myOrder")
    public ResponseEntity<?> countMyOrder() {
        log.info("START API /api/v1/order/count/myOrder");
        return ResponseEntity.ok(service.orderService.countMyOrders(getCurrentUser()));
    }

    @Operation(summary = "API getDetail Order")
    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderDetail(@PathVariable("id") String id) {
        log.info("START API /api/v1/order/id");
        return ResponseEntity.ok(service.orderDetailService.getOrderDetail(getCurrentUser(),id));
    }

    @Operation(summary = "API hủy đơn hàng")
    @PostMapping("/cancelOrder")
    public ResponseEntity<?> cancelOrder(@RequestBody CancelOrder req) {
        log.info("START API /api/v1/order/cancelOrder");
        return ResponseEntity.status(HttpStatus.OK).body(service.orderService.cancelOrder(getCurrentUser(), req));
    }

    @PostMapping("/search")
    @Operation(summary = "Search Order")
    public ResponseEntity<Object> search(@RequestBody OrderSearch req) {
        log.info("start api /api/v1/order/search with payload: {}", JsonUtils.toJson(req));
        return ResponseEntity.ok(service.orderService.search(getCurrentUser(), req));
    }


    @Operation(summary = "Submit ORDER RECEIVED")
    @PostMapping("/submit/received")
    public ResponseEntity<?> submitReceivedOrder(@RequestBody SubmitOrderReceived req) {
        log.info("start api /api/v1/order//submit/received with payload: {}", JsonUtils.toJson(req));
        service.orderService.submitReceivedOrder(getCurrentUser(), req);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
