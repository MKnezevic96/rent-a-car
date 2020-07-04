package com.zuul.controller;

import io.spring.guides.gs_producing_web_service.GetCarsRequest;
import io.spring.guides.gs_producing_web_service.GetCarsResponse;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

public class CarsClient extends WebServiceGatewaySupport {



    public GetCarsResponse getCars (Integer carsId) {

//        GetCarsRequest request = new GetCarsRequest();
//        request.setCarId(carsId);
//        GetCarsResponse response = (GetCarsResponse) getWebServiceTemplate()
//                .marshalSendAndReceive("http://localhost:8282/ws/", request,
////                        new SoapActionCallback(
////                                "http://localhost:8083/GetCarsResponse"));
//               null);
//        return response;

        GetCarsRequest request = new GetCarsRequest();
        request.setCarId(carsId);

        return (GetCarsResponse) getWebServiceTemplate().marshalSendAndReceive(request);
    }

}
