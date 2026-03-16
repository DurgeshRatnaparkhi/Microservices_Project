package com.employee;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@Slf4j
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class MicroserviceEmployeeApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceEmployeeApplication.class, args);

        log.info("Employee microservice is running...");
	}

}
