package com.renting_service;

import com.renting_service.controller.CarsClient;
import io.spring.guides.gs_producing_web_service.GetCarsResponse;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;

@SpringBootApplication(scanBasePackages={"com.renting_service.service", "com.renting_service.config.soap"})
@RestController
//@EnableEurekaClient
public class RentingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RentingServiceApplication.class, args);
	}

	@RequestMapping("/health")
	public String sayHello(){
		return "Hello from renting service!";
	}


	@RequestMapping("/soapTest")
	ResponseEntity lookup(CarsClient quoteClient) {
//		BigInteger cars = BigInteger.valueOf(1);

//		GetCarsResponse response = quoteClient.getCars(cars);
//		System.err.println(response.getCars());
//		return response;
		System.out.println("      > Pokusavam da posaljem zahtev");
		BigInteger cars = BigInteger.valueOf(1);
		GetCarsResponse response = quoteClient.getCars(cars);
		System.out.println("      > Ime auta koji sam dobio: " + response.getCars().getName());
		System.out.println("        - Model: " + response.getCars().getModel());
		System.out.println("        - Auto koji sam dobio: " + response.getCars().getManufacturer());
		String retVal = "      > Ime auta koji sam dobio: " + response.getCars().getName() + "/n"
				+ "        - Model: " + response.getCars().getModel() + "/n"
				+ "        - Auto koji sam dobio: " + response.getCars().getManufacturer();
		return new ResponseEntity<String> (retVal, HttpStatus.OK);
	}


}
