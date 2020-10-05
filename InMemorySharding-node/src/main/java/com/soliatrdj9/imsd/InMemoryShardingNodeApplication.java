package com.soliatrdj9.imsd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@EnableDiscoveryClient
public class InMemoryShardingNodeApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(InMemoryShardingNodeApplication.class, args);

		//((ServiceManager)context.getBean("serviceManager")).registerService();
	}
}