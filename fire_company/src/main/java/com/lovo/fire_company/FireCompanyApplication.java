package com.lovo.fire_company;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class FireCompanyApplication {

    public static void main(String[] args) {
        SpringApplication.run(FireCompanyApplication.class, args);
    }

}
