package com.dk.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DkAppGatewayServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(DkAppGatewayServerApplication.class, args);
	}
	@Bean
	RouteLocator gatewayRoute(RouteLocatorBuilder builder) {
		return builder.routes()
				.route("authRoute", routeSepc -> routeSepc
						.path("/**")
						.filters(f -> f.addResponseHeader("DK_APP", "System Header")) // Using this filter u can add things to the response (End Response : Which is going to the customer)
						.uri("lb://AUTH-SERVER") // This will load balance (lb)
				)
				.build();

	}
}
