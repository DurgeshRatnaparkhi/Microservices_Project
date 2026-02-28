package com.address;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class AddressMicroservicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(AddressMicroservicesApplication.class, args);

        log.info("Address Microservices Application is running...");
	}

}
