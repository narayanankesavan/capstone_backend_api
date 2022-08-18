package com.iitr.gl.userdetailservice.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "loginservice")
public interface LoginServiceClient {
    @GetMapping("/login_service/test")
    @CircuitBreaker(name = "loginservice", fallbackMethod = "testRemoteFallback")
    public String testRemote();

    default String testRemoteFallback(Throwable exception) {
        System.out.println("Exception :: " + exception.getStackTrace());
        return "We are facing service disruption and working on to fix it, please check after sometime";
    }
}
