package com.iitr.gl.loginservice.ui.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login_service/")
public class LogInController {

    @GetMapping("/test")
    public String testMethod() {
        return "Hello from remote micro-service";
    }
}