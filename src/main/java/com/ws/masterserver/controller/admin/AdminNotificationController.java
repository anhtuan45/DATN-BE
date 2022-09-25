package com.ws.masterserver.controller.admin;

import com.ws.masterserver.utils.base.WsController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/notification")
@RequiredArgsConstructor
@Slf4j
public class AdminNotificationController extends WsController {

    @GetMapping("top3")
    public ResponseEntity<Object> getNotification4Admin()  {
        return ResponseEntity.ok(service.notificationService.get4Admin(getCurrentUser()));
    }

    @PostMapping("read-top3")
    public ResponseEntity<Object> readTop3Notification4Admin(@RequestBody List<String> dto) {
        return ResponseEntity.ok(service.notificationService.readTop3Notification4Admin(getCurrentUser(), dto));
    }

    @GetMapping("/search")
    public ResponseEntity<Object> search(@RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        return ResponseEntity.ok(service.notificationService.search4Admin(getCurrentUser(), pageSize));
    }

    @GetMapping("/read-all")
    public ResponseEntity<Object> readAll() {
        return ResponseEntity.ok(service.notificationService.readAll4Admin(getCurrentUser()));
    }

    @GetMapping("/read")
    public ResponseEntity<Object> readById(String id) {
        service.notificationService.readById4Admin(getCurrentUser(), id);
        return ResponseEntity.status(HttpStatus.OK).body(true);
    }


}
