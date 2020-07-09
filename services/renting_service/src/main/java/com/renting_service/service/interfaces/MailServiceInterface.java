package com.renting_service.service.interfaces;

import com.sun.xml.messaging.saaj.packaging.mime.MessagingException;

import java.io.IOException;

public interface MailServiceInterface {

    void sendEmail(String sendTo, String text, String subject) throws MessagingException, IOException, javax.mail.MessagingException;
}