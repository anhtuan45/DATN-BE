package com.ws.masterserver.controller;

import com.ws.masterserver.utils.base.WsController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/notification")
@RequiredArgsConstructor
@Slf4j
public class NotificationController extends WsController {

    @GetMapping("/top3")
    public ResponseEntity<Object> getNotification4Admin()  {
        return ResponseEntity.ok(service.notificationService.getTop3Noti4Customer(getCurrentUser()));
    }

    @GetMapping("/search")
    public ResponseEntity<Object> search(@RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        return ResponseEntity.ok(service.notificationService.get4Customer(getCurrentUser(), pageSize));
    }

    @GetMapping("/read-all")
    public ResponseEntity<Object> readAll() {
        return ResponseEntity.ok(service.notificationService.readAll4Customer(getCurrentUser()));
    }

    @GetMapping("/read")
    public ResponseEntity<Object> readById(String id) {
        service.notificationService.readNotification4Customer(getCurrentUser(), id);
        return ResponseEntity.status(HttpStatus.OK).body(true);
    }
}
