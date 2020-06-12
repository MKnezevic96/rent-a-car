package com.rent_a_car.agentski_bekend.service;

import com.rent_a_car.agentski_bekend.model.Cars;
import com.rent_a_car.agentski_bekend.repository.CarsRepository;
import io.spring.guides.gs_producing_web_service.CarsSoap;
import io.spring.guides.gs_producing_web_service.GetCarsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class SoapService {

    private static final String NAMESPACE_URI = "http://spring.io/guides/gs-producing-web-service";

    @Autowired
    private CarsRepository carsRepository;

//    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCountryRequest")
//    @ResponsePayload
//    public GetCarsResponse getCountry(@RequestPayload GetCarsResponse request) {
//        GetCarsResponse response = new GetCountryResponse();
//        response.setCars(countryRepository.findCountry(request.getName()));
//
//        return response;
//    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCarsRequest")
    @ResponsePayload
    public GetCarsResponse getCar () {
        System.out.println("Pogodio si @Endpoint; Svaka ti dala! =D");
        GetCarsResponse response = new GetCarsResponse();
//        Cars car = carsRepository.getOne(1);
//        System.out.println("Car name: " + car.getName());
        CarsSoap carsSoap = new CarsSoap();
        carsSoap.setName("Ford Mustang");
        carsSoap.setManufacturer("Ford");
        carsSoap.setModel("Mustang");
        response.setCars(carsSoap);
        return response;
    }

}
