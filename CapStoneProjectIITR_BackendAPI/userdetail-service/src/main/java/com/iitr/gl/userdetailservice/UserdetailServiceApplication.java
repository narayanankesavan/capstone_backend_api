package com.iitr.gl.userdetailservice;

import com.iitr.gl.userdetailservice.util.GetJwtSubject;
import feign.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class UserdetailServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserdetailServiceApplication.class, args);
    }

    @Bean
    Logger.Level setFeignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Bean
    public GetJwtSubject getJwtSubject() {
        return new GetJwtSubject();
    }

}



