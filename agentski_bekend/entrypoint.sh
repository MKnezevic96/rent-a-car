#!/bin/sh

./consul agent -config-dir=/consul-config &
 
java -jar agentski_bekend-0.0.1-SNAPSHOT.jar -Djavax.net.ssl.keyStore=/src/main/resources/agentski-bekend.dc1.rent-a-car.keystore.p12 -Djavax.net.ssl.trustStore=/src/main/resources/rent-a-car.truststore.p12 
