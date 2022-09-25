package com.ws.masterserver.controller;

import com.ws.masterserver.utils.base.WsController;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController extends WsController {

//    @GetMapping("/test-send")
//    public ResponseEntity<Object> test(String email) {
//        return ResponseEntity.ok(service.mailService.test(email));
//    }

}
