package com.iitr.gl.loginservice.controller;

import com.iitr.gl.loginservice.model.LoginDetail;
import com.iitr.gl.loginservice.repository.MySqlLoginRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
@RefreshScope
public class LoginController {
    private final MySqlLoginRepository mySqlLoginRepository;
    @Value("${testvalue}")
    private String value;

    @PostMapping(path = "/patient/login", produces = {"application/json"})
    public ResponseEntity<String> getLoginDetails(@RequestBody LoginDetail loginDetail) {
        LoginDetail result = mySqlLoginRepository.findUserByEmail(loginDetail.getUser_email());
        if (result == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(("User not found"));
        } else return ResponseEntity.status(HttpStatus.FOUND).body(("User found"));
    }

    @GetMapping("/test")
    public String test() {
        return value;
    }
}
