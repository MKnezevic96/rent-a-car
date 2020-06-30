FROM nginx:latest
RUN apt-get update 
RUN apt-get install -y openssl
RUN mkdir -p /etc/nginx/certs/self-signed/
RUN openssl req -x509 -nodes -days 365 -newkey rsa:2048 -keyout /etc/nginx/certs/self-signed/ssldocker.test.key -out /etc/nginx/certs/self-signed/ssldocker.test.crt -subj "/C=RS/ST=Serbia/L=NS/O=rent-a-car/OU=rent-a-car/CN=ssldocker.test"
RUN openssl dhparam -out /etc/nginx/certs/dhparam.pem 2048
