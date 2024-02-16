package com.dk.app;

import com.netflix.discovery.EurekaClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.client.RestTemplate;

import java.net.http.HttpClient;

@SpringBootApplication
@EnableEurekaClient
public class DkAppAuthJwtApplication {

	public static void main(String[] args) {
		SpringApplication.run(DkAppAuthJwtApplication.class, args);
	}

	@Bean
	@LoadBalanced
	public HttpClient getHttpClient(){
		return HttpClient.newHttpClient();
	}


}
