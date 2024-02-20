package com.dk.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.transaction.annotation.Transactional;

@SpringBootApplication
@EnableEurekaClient
public class DkAppCustomerCamundaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(DkAppCustomerCamundaServerApplication.class, args);
	}

}