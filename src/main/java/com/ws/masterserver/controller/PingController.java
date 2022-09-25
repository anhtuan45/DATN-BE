package com.ws.masterserver.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
@Slf4j
public class PingController {

    @Value(value = "${spring.datasource.username}")
    private String username;
    @Value(value = "${spring.datasource.password}")
    private String password;
    @Value(value = "${spring.datasource.url}")
    private String url;

    @RequestMapping("/ping")
    public Object ping() {
        log.info("Start api /ping");
        return "Ping successfully!";
    }

    @RequestMapping("get-info")
    public Object getInfo() {
        return username + " | " + url;
    }
}
