package com.admin_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
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
