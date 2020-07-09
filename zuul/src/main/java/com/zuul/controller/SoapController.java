package com.zuul.controller;


import io.spring.guides.gs_producing_web_service.CarsSoap;
import io.spring.guides.gs_producing_web_service.GetCarsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.soap.*;
import java.io.ByteArrayOutputStream;

@RestController
@RequestMapping("/soap/")
public class SoapController {

    // Todo: ovde dodati zahtev za poruku koji ce ici na agenta

    @Autowired
    CarsClient carsClient;

    @GetMapping("car/{id}")
    ResponseEntity lookup(CarsClient quoteClient, @PathVariable("id") Integer id) {

        System.out.println("   >    <   ");
        System.out.println("   >    <   ");
        System.out.println("   >    <   ");
        System.out.println("   >  You found me, says SoapController  <   ");
        System.out.println("   >    <   ");
        System.out.println("   >    <   ");
        System.out.println("   >    <   ");

//        Integer id = 5;
//        GetCarsResponse response = carsClient.getCars(id);
//        return new ResponseEntity<CarsSoap>(response.getCars(), HttpStatus.OK);


        String soapEndpointUrl = "http://172.30.0.1:8282/ws";
        String soapAction = "getCarsRequest";

        return new ResponseEntity<String>(callSoapWebService(soapEndpointUrl, soapAction, id), HttpStatus.OK);
    }


    private static void createSoapEnvelope(SOAPMessage soapMessage, Integer id) throws SOAPException {
        SOAPPart soapPart = soapMessage.getSOAPPart();

        String myNamespace = "gs";
        String myNamespaceURI = "http://spring.io/guides/gs-producing-web-service";

        // SOAP Envelope
        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration(myNamespace, myNamespaceURI);

            /*
            Constructed SOAP Request Message:
            <SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/" xmlns:myNamespace="http://www.webserviceX.NET">
                <SOAP-ENV:Header/>
                <SOAP-ENV:Body>
                    <myNamespace:GetInfoByCity>
                        <myNamespace:USCity>New York</myNamespace:USCity>
                    </myNamespace:GetInfoByCity>
                </SOAP-ENV:Body>
            </SOAP-ENV:Envelope>
            */

        // SOAP Body
        SOAPBody soapBody = envelope.getBody();
        SOAPElement soapBodyElem = soapBody.addChildElement("getCarsRequest", myNamespace);
        SOAPElement soapBodyElem1 = soapBodyElem.addChildElement("carId", myNamespace);
        soapBodyElem1.addTextNode(id.toString());
    }

    private static String callSoapWebService(String soapEndpointUrl, String soapAction, Integer id) {
        String retVal = "Nemam odgovor :p";
        try {
            // Create SOAP Connection
            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection soapConnection = soapConnectionFactory.createConnection();

            // Send SOAP Message to SOAP Server
            SOAPMessage soapResponse = soapConnection.call(createSOAPRequest(soapAction, id), soapEndpointUrl);

            // Print the SOAP Response
            System.out.println("Response SOAP Message:");

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            soapResponse.writeTo(out);
            retVal = new String(out.toByteArray());


            soapResponse.writeTo(System.out);
            System.out.println();

//            retVal = soapResponse.getAtt;


//            soapConnection.writeTo(retVal.in);

        } catch (Exception e) {
            System.err.println("\nError occurred while sending SOAP Request to Server!\nMake sure you have the correct endpoint URL and SOAPAction!\n");
            e.printStackTrace();
        }

        return retVal;
    }

    private static SOAPMessage createSOAPRequest(String soapAction, Integer id) throws Exception {
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();

        createSoapEnvelope(soapMessage, id);

        MimeHeaders headers = soapMessage.getMimeHeaders();
        headers.addHeader("getCarsRequest", soapAction);

        soapMessage.saveChanges();

        /* Print the request message, just for debugging purposes */
        System.out.println("Request SOAP Message:");
        soapMessage.writeTo(System.out);
        System.out.println("\n");

        return soapMessage;
    }



}
