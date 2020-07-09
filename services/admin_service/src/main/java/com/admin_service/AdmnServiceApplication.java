package com.admin_service;

import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.shared.transport.jersey.EurekaJerseyClientImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;

@SpringBootApplication(scanBasePackages={
		"com.admin_service.service",
		"com.admin_service.security",
		"com.admin_service.config",
		"com.admin_service.service.interfaces",
		"com.admin_service.helper"
})

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

//	@Bean
//	public DiscoveryClient.DiscoveryClientOptionalArgs discoveryClientOptionalArgs() throws NoSuchAlgorithmException {
//		DiscoveryClient.DiscoveryClientOptionalArgs args = new DiscoveryClient.DiscoveryClientOptionalArgs();
//		System.setProperty("javax.net.ssl.keyStore", "/home/student/rent-a-car/services/admin_service/src/main/resources/admin-service.dc1.keystore.p12");
//		System.setProperty("javax.net.ssl.keyStorePassword", "739476603727");
//		System.setProperty("javax.net.ssl.trustStore", "/home/student/rent-a-car/services/admin_service/src/main/resources/rent-a-car.truststore.p12");
//		System.setProperty("javax.net.ssl.trustStorePassword", "739476603727");
//		EurekaJerseyClientImpl.EurekaJerseyClientBuilder builder = new EurekaJerseyClientImpl.EurekaJerseyClientBuilder();
//		builder.withClientName("account-client");
//		builder.withSystemSSLConfiguration();
//		builder.withMaxTotalConnections(10);
//		builder.withMaxConnectionsPerHost(10);
//		args.setEurekaJerseyClient(builder.build());
//		return args;
//	}
}

