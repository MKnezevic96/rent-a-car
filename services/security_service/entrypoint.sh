#!/bin/sh

./consul agent -config-dir=/consul-config &

java -jar security_service-0.0.1-SNAPSHOT.jar -Djavax.net.ssl.keyStore=services/security-service/src/main/resources/security-service.dc1.rent-a-car.keystore.p12  
