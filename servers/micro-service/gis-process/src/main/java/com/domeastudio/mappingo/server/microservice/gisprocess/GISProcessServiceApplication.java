package com.domeastudio.mappingo.server.microservice.gisprocess;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Created by domea on 17-5-21.
 */
@SpringBootApplication
@EnableDiscoveryClient
public class GISProcessServiceApplication {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(GISProcessServiceApplication.class, args);
    }
}
