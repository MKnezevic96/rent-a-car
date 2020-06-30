package com.renting_service.controller;

import com.renting_service.config.soap.WebServiceConfig;
import io.spring.guides.gs_producing_web_service.GetCarsRequest;
import io.spring.guides.gs_producing_web_service.GetCarsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;



import java.math.BigInteger;

public class CarsClient extends WebServiceGatewaySupport {

    public CarsClient () {
        this.setMarshaller(WebServiceConfig.jaxb2Marshaller());
        this.setUnmarshaller(WebServiceConfig.jaxb2Marshaller());
    }



    public GetCarsResponse getCars (BigInteger id) {
        GetCarsRequest request = new GetCarsRequest();
        //id = BigInteger.valueOf(5);
        request.setName(id);

        GetCarsResponse response = (GetCarsResponse) getWebServiceTemplate ()
                .marshalSendAndReceive("http://localhost:8282/ws/cars", request,
                        new SoapActionCallback(
                                "http://spring.io/guides/gs-producing-web-service/GetCarsRequest"
                        ));
        return response;
    }



}
