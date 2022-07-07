package com.iitr.gl.patientdetailservice;

import feign.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class PatientdetailServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PatientdetailServiceApplication.class, args);
    }

    @Bean
    Logger.Level setFeignLoggerLevel() {
        return Logger.Level.FULL;
    }
}
