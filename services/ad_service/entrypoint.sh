#!/bin/sh

./consul agent -config-dir=/consul-config &

java -jar ad_service-0.0.1-SNAPSHOT.jar 
