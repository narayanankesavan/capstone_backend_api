package com.iitr.gl.patientdetailservice.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "loginservice")
public interface LoginServiceClient {

    @GetMapping("/login_service/testss")
    public String testRemote();
}
