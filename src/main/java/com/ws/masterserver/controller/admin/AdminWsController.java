package com.ws.masterserver.controller.admin;

import com.ws.masterserver.service.WebSocketService;
import com.ws.masterserver.utils.constants.enums.NotificationTypeEnum;
import com.ws.masterserver.utils.constants.enums.ObjectTypeEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/ws/test")
@RequiredArgsConstructor
public class AdminWsController {
    private final WebSocketService wsService;

    @GetMapping("/notification")
    public ResponseEntity<Object> testNotification4Admin(NotificationTypeEnum type, String content, ObjectTypeEnum objectType, String objectTypeId) {
        wsService.testNotification4Admin(type, content, objectType, objectTypeId);
        return ResponseEntity.ok(true);
    }

    @GetMapping("/dashboard")
    public ResponseEntity<Object> testDashboard4Admin(int type) {
        wsService.testDashboard4Admin(type);
        return ResponseEntity.ok(true);
    }

    @GetMapping("/v2")
    public ResponseEntity<Object> v2(String message) {
        wsService.testV2(message);
        return ResponseEntity.ok(true);
    }

}
