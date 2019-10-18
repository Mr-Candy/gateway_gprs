package com.gateway;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.gateway.dao")
public class GprsGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GprsGatewayApplication.class, args);
    }

}
