package com.rent_a_car.agentski_bekend.service.interfaces;

import com.rent_a_car.agentski_bekend.model.Message;
import com.rent_a_car.agentski_bekend.model.User;
import com.sun.xml.messaging.saaj.packaging.mime.MessagingException;

import java.io.IOException;
import java.util.List;

public interface MessageServiceInterface {
    List<User> findAllToUsers(String email);
    List<User> findAllFromUsers(String email);
    List<Message> findAll();
    void sendMessageEmail(String sendFrom, String sendTo) throws MessagingException, IOException, javax.mail.MessagingException;

    }
