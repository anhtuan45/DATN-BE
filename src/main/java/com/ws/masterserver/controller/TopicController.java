package com.ws.masterserver.controller;

import com.ws.masterserver.dto.customer.blog.BlogResponse;
import com.ws.masterserver.utils.base.WsController;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/topic")
@RequiredArgsConstructor
@Slf4j
public class TopicController extends WsController {
 //ok
    @Operation(summary = "API lấy tất cả topic")
    @GetMapping("")
    public ResponseEntity<?> getAllTopic() {
        log.info("START API /api/v1/topic");
        return ResponseEntity.status(HttpStatus.OK).body(service.topicService.getAllTopic());
    }
}
