package com.soliatrdj9.imsd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class InMemoryShardingApplication {

	public static void main(String[] args) {
		SpringApplication.run(InMemoryShardingApplication.class, args);
	}
}