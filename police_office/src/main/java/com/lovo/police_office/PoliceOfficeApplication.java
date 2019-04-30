package com.lovo.police_office;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class PoliceOfficeApplication {

    public static void main(String[] args) {
        SpringApplication.run(PoliceOfficeApplication.class, args);
    }

}
