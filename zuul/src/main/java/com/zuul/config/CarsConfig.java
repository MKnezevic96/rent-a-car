package com.zuul.config;


import com.zuul.controller.CarsClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class CarsConfig {

    @Bean
    public Jaxb2Marshaller marshaller () {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        System.out.println(" >> >> >> >> Kreiram marshaler << << << <<");
        marshaller.setContextPath("io.spring.guides.gs_producing_web_service");
        return marshaller;
    }

    //io.spring.guides.gs_producing_web_service.CarsSoap
    //zuul/target/generated-sources/jaxb/io/spring/guides/gs_producing_web_service/CarsSoap.java
    @Bean
    public CarsClient carsClient (Jaxb2Marshaller marshaller) {
        CarsClient client = new CarsClient();
        client.setDefaultUri("http://localhost:8282/ws");
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }

}
