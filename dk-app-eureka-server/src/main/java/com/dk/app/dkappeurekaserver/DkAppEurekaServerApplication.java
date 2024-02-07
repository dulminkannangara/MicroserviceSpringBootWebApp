package com.dk.app.dkappeurekaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class DkAppEurekaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(DkAppEurekaServerApplication.class, args);
	}

}
