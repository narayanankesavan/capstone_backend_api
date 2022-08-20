package com.iitr.gl.loginservice.ui.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login_service/")
public class LogInController {

    Logger logger = LoggerFactory.getLogger(LogInController.class);

    @GetMapping("/test")
    public String testMethod() {
        logger.info("Test endpoint of loginservice called");
        return "Hello from remote micro-service";
    }
}