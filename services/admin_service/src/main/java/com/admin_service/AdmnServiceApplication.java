package com.admin_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication(scanBasePackages={
		"com.admin_service.service"})
@RestController
@EnableEurekaClient
public class AdmnServiceApplication {

	@RequestMapping("/health")
	public String home() {
		return "Hello admin service!";
	}

	public static void main(String[] args) {
		SpringApplication.run(AdmnServiceApplication.class, args);
	}


}
