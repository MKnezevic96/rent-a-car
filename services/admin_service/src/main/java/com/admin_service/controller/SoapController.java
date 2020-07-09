package com.admin_service.controller;

import com.admin_service.model.Cars;
import com.admin_service.service.CarsService;
import io.spring.guides.gs_producing_web_service.CarsSoap;
import io.spring.guides.gs_producing_web_service.GetCarsRequest;
import io.spring.guides.gs_producing_web_service.GetCarsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
//@RestController
//@RequestMapping(value = "ws/")
public class SoapController {

    private static final String NAMESPACE_URI = "http://spring.io/guides/gs-producing-web-service";

    @Autowired
    private CarsService carsService;

    //    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCountryRequest")
//    @ResponsePayload
//    public GetCarsResponse getCountry(@RequestPayload GetCarsResponse request) {
//        GetCarsResponse response = new GetCountryResponse();
//        response.setCars(countryRepository.findCountry(request.getName()));
//
//        return response;
//    }
//
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCarsRequest")
    @ResponsePayload
    public GetCarsResponse getCar (@RequestPayload GetCarsRequest request) {

        System.out.println("Pogodio si @Endpoint; Svaka ti dala! =D");
        GetCarsResponse response = new GetCarsResponse();
        System.out.println("Loading car...");
        Cars car = carsService.getCar(request.getCarId());
        System.out.println("Car name: " + car.getName());
        CarsSoap carsSoap = new CarsSoap();
        carsSoap.setCarName(car.getName());
        carsSoap.setManufacturer(car.getModel().getManufacturer().getName());
        carsSoap.setModel(car.getModel().getName());
        response.setCars(carsSoap);
        return response;
    }

}
