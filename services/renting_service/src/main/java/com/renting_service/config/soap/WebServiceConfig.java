package com.renting_service.config.soap;

import com.renting_service.controller.CarsClient;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {

    public WebServiceConfig () {
        System.out.println("///////////////////////////////////////////////////////");
        System.out.println("///////////////////////////////////////////////////////");
        System.out.println("///////////////////////////////////////////////////////");
        System.out.println("///////////////////////////////////////////////////////");
        System.out.println("Konfigurisem WebServiceConfig");
        System.out.println("///////////////////////////////////////////////////////");
        System.out.println("///////////////////////////////////////////////////////");
        System.out.println("///////////////////////////////////////////////////////");
        System.out.println("///////////////////////////////////////////////////////");
    }

    @Bean
    public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext) {
        System.out.println("///////////////////////////////////////////////////////");
        System.out.println("///////////////////////////////////////////////////////");
        System.out.println("///////////////////////////////////////////////////////");
        System.out.println("///////////////////////////////////////////////////////");
        System.out.println("Konfigurisem WebServiceConfig");
        System.out.println("///////////////////////////////////////////////////////");
        System.out.println("///////////////////////////////////////////////////////");
        System.out.println("///////////////////////////////////////////////////////");
        System.out.println("///////////////////////////////////////////////////////");
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean(servlet, "/ws/*");
    }

    @Bean(name = "cars")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema carsSchema) {
        System.out.println("///////////////////////////////////////////////////////");
        System.out.println("///////////////////////////////////////////////////////");
        System.out.println("///////////////////////////////////////////////////////");
        System.out.println("///////////////////////////////////////////////////////");
        System.out.println("Konfigurisem WebServiceConfig");
        System.out.println("///////////////////////////////////////////////////////");
        System.out.println("///////////////////////////////////////////////////////");
        System.out.println("///////////////////////////////////////////////////////");
        System.out.println("///////////////////////////////////////////////////////");
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("CarsPort");
        wsdl11Definition.setLocationUri("/ws/");
        wsdl11Definition.setTargetNamespace("http://spring.io/guides/gs-producing-web-service");
        wsdl11Definition.setSchema(carsSchema);

        return wsdl11Definition;
    }

    @Bean
    public XsdSchema carsSchema() {
        System.out.println("///////////////////////////////////////////////////////");
        System.out.println("///////////////////////////////////////////////////////");
        System.out.println("///////////////////////////////////////////////////////");
        System.out.println("///////////////////////////////////////////////////////");
        System.out.println("Konfigurisem WebServiceConfig");
        System.out.println("///////////////////////////////////////////////////////");
        System.out.println("///////////////////////////////////////////////////////");
        System.out.println("///////////////////////////////////////////////////////");
        System.out.println("///////////////////////////////////////////////////////");
        return new SimpleXsdSchema(new ClassPathResource("cars.xsd"));
    }

    @Bean
    public static Jaxb2Marshaller jaxb2Marshaller() {
        System.out.println("///////////////////////////////////////////////////////");
        System.out.println("///////////////////////////////////////////////////////");
        System.out.println("///////////////////////////////////////////////////////");
        System.out.println("///////////////////////////////////////////////////////");
        System.out.println("Konfigurisem Marshaller");
        System.out.println("///////////////////////////////////////////////////////");
        System.out.println("///////////////////////////////////////////////////////");
        System.out.println("///////////////////////////////////////////////////////");
        System.out.println("///////////////////////////////////////////////////////");
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        // this package must match the package in the <generatePackage> specified in
        // pom.xml
        marshaller.setPackagesToScan("io.spring.guides.gs_producing_web_service");
        return marshaller;
    }

    @Bean
    public CarsClient carsClient(Jaxb2Marshaller jaxb2Marshaller) {
        System.out.println("///////////////////////////////////////////////////////");
        System.out.println("///////////////////////////////////////////////////////");
        System.out.println("///////////////////////////////////////////////////////");
        System.out.println("///////////////////////////////////////////////////////");
        System.out.println("Konfigurisem CarsKlijenta");
        System.out.println("///////////////////////////////////////////////////////");
        System.out.println("///////////////////////////////////////////////////////");
        System.out.println("///////////////////////////////////////////////////////");
        System.out.println("///////////////////////////////////////////////////////");
        CarsClient client = new CarsClient();
        client.setDefaultUri("http://localhost:8282/ws");
        client.setMarshaller(jaxb2Marshaller);
        client.setUnmarshaller(jaxb2Marshaller);
        return client;
    }


}