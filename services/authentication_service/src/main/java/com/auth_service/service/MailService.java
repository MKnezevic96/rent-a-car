package com.auth_service.service;

import com.auth_service.service.interfaces.MailServiceInterface;
import com.sun.xml.messaging.saaj.packaging.mime.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.io.IOException;

@Service
public class MailService implements MailServiceInterface {

    @Autowired
    private JavaMailSender javaMailSender;


    @Override
    public void sendEmail(String sendTo, String text, String subject) throws MessagingException, IOException, javax.mail.MessagingException {

        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);

        helper.setTo(sendTo);
        helper.setSubject(subject);
        helper.setText(text);

        javaMailSender.send(msg);

    }

}