package com.zuul.controller;

import io.spring.guides.gs_producing_web_service.CarsSoap;
import io.spring.guides.gs_producing_web_service.GetCarsRequest;
import io.spring.guides.gs_producing_web_service.GetCarsResponse;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class SoapEndpoint {

    private static final String NAMESPACE_URI = "http://spring.io/guides/gs-producing-web-service";

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCarsRequest")
    @ResponsePayload
    public GetCarsResponse getCountry(@RequestPayload GetCarsRequest request) {
        GetCarsResponse response = new GetCarsResponse();
        response.setCars(new CarsSoap());
        response.getCars().setCarName("Georgije");
        response.getCars().setModel("Civic");
        response.getCars().setManufacturer("Honda");
        return response;
    }




}
