package com.iitr.gl.patientdetailservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class PatientdetailServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PatientdetailServiceApplication.class, args);
    }

}
