package com.rent_a_car.agentski_bekend.service;

import com.rent_a_car.agentski_bekend.controller.RentingController;
import com.rent_a_car.agentski_bekend.model.Message;
import com.rent_a_car.agentski_bekend.model.User;
import com.rent_a_car.agentski_bekend.repository.MessageRepository;
import com.rent_a_car.agentski_bekend.service.interfaces.MessageServiceInterface;
import com.sun.xml.messaging.saaj.packaging.mime.MessagingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class MessageService implements MessageServiceInterface {

    @Autowired
    private UserService userService;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    private static final Logger LOGGER = LogManager.getLogger(RentingController.class.getName());



    @Override
    public List<User> findAllToUsers(String email) {
        List<User> retVal = new ArrayList<>();

        User u = userService.findByEmail(email);
        List<Message> sentMessages = u.getSentMessages();

        for (Message m : sentMessages){
            retVal.add(m.getTo());
        }

        return retVal;
    }

    @Override
    public List<User> findAllFromUsers(String email) {

        List<User> retVal = new ArrayList<>();

        User u = userService.findByEmail(email);
        List<Message> recievedMessages = u.getRecieved();

        for (Message m : recievedMessages){
           retVal.add(m.getFrom());
        }

        return retVal;
    }

    @Override
    public List<Message> findAll() {
        return messageRepository.findAll();
    }

    public Message save(Message message){
        return messageRepository.save(message);
    }

    @Override
    public void sendMessageEmail(String sendFrom, String sendTo) throws MessagingException, IOException, javax.mail.MessagingException {

        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        helper.setTo(sendTo);

        helper.setSubject("New message received!");
        String text = "Dear sir/madam, " + '\n';
        text += "you have received a new message from user ." + sendFrom;
        text +=  "\n\n\n" + "Sincerely, Rent a car support team.";

        helper.setText(text);

        try {
            javaMailSender.send(msg);
            LOGGER.info("Email to account: {} has been sent", sendTo);
        } catch (Exception e) {
            LOGGER.warn("Email to account: {} has NOT been sent. Cause: {}", sendTo, e.getMessage());
        }

    }
}
