package com.admin_service.controller;


import com.admin_service.model.Cars;
import com.admin_service.service.CarsService;
import io.spring.guides.gs_producing_web_service.CarsSoap;
import io.spring.guides.gs_producing_web_service.GetCarsRequest;
import io.spring.guides.gs_producing_web_service.GetCarsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
@RestController("/ws")
public class CarsEndpoint {

    private static final String NAMESPACE_URI = "http://spring.io/guides/gs-producing-web-service";

    @Autowired
    CarsService carsService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCarsRequest")
    @ResponsePayload
    public GetCarsResponse getCarsResponse (@RequestPayload GetCarsRequest request) {
        CarsSoap car = new CarsSoap();
        Cars original = carsService.getCar(1);
        car.setName(original.getName());
        car.setModel(original.getModel().getName());
        car.setManufacturer(original.getModel().getManufacturer().getName());
        GetCarsResponse response = new GetCarsResponse();
        response.setCars(car);

        return response;
    }

}
