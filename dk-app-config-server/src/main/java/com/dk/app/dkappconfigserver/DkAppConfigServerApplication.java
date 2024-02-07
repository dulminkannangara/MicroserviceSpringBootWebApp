package com.dk.app.dkappconfigserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class DkAppConfigServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(DkAppConfigServerApplication.class, args);
	}

}
