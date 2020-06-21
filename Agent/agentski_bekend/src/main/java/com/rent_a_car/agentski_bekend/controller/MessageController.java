package com.rent_a_car.agentski_bekend.controller;

import com.rent_a_car.agentski_bekend.dto.*;
import com.rent_a_car.agentski_bekend.model.*;
import com.rent_a_car.agentski_bekend.service.MessageService;
import com.rent_a_car.agentski_bekend.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.core.MediaType;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "api/messages/")
public class MessageController {

    private static final Logger LOGGER = LogManager.getLogger(RentingController.class.getName());

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @GetMapping(value = "users")
    public ResponseEntity<List<UserDTO>> getAllUsers (@RequestParam(value = "param", required = false) String param, Principal p) {
        List<UserDTO> retVal = new ArrayList<UserDTO>();
        List<String> emails = new ArrayList<>();
        User currentUser = userService.findByEmail(p.getName());
        try {
            if(param == null){
                for (User u : messageService.findAllToUsers(p.getName())) {
                    emails.add(u.getEmail());
                }
                for (User u : messageService.findAllFromUsers(p.getName())) {
                    emails.add(u.getEmail());
                }
                LOGGER.info("Action get all message conversations of user: {} successful", p.getName());
            } else if (param.equals("to")) {
                for (User u : messageService.findAllToUsers(p.getName())) {
                    emails.add(u.getEmail());
                }
                LOGGER.info("Action get all message recievers of user: {} successful", p.getName());
            } else if (param.equals("from")) {
                for (User u : messageService.findAllFromUsers(p.getName())) {
                    emails.add(u.getEmail());
                }
                LOGGER.info("Action get all message senders of user: {} successful", p.getName());
            }

            for(RentRequest rr : currentUser.getRentRequests() ){
                if(!rr.getStatus().equals("pending"))
                    emails.add(rr.getCarId().getOwner().getEmail());
            }

            Set<String> set = new HashSet<>(emails);
            emails.clear();
            emails.addAll(set);

            for(String email:emails){
                retVal.add(new UserDTO(userService.findByEmail(email)));
            }

            return new ResponseEntity<List<UserDTO>>(retVal, HttpStatus.OK);
        } catch(Exception e){
            LOGGER.error("Action get all message conversations of user: {} failed. Cause: {}", p.getName(), e.getMessage());
        }

        return new ResponseEntity<List<UserDTO>>(retVal, HttpStatus.BAD_REQUEST);
    }


    @GetMapping(value = "history")
    public ResponseEntity<List<MessageDTO>> getMessageHistory (@RequestParam(value = "email", required = false) String email,  Principal p) {
        List<MessageDTO> retVal = new ArrayList<MessageDTO>();
        User user = userService.findByEmail(p.getName());

        for(Message m : user.getSentMessages()){
            if(m.getTo().getEmail().equals(email)){
                retVal.add(new MessageDTO(m));
            }
        }

        for(Message m : user.getRecieved()){
            if(m.getFrom().getEmail().equals(email)){
                retVal.add(new MessageDTO(m));
            }
        }

        retVal.sort(Comparator.comparing(MessageDTO::getDate));

        LOGGER.info("Action get message history of user: {} with user {} successful", p.getName(), email);
        return new ResponseEntity<List<MessageDTO>>(retVal, HttpStatus.OK);
    }


    @PostMapping(value="message", consumes = MediaType.APPLICATION_JSON)
    public ResponseEntity<?> sendMessage(@RequestBody MessageDTO dto, Principal p){

        try{
            Message message = new Message();
            message.setContent(dto.getContent());
            message.setDate(new Date());
            message.setDeleted(false);


            User sender = userService.findByEmail(p.getName());
            message.setFrom(sender);
            User receiver = userService.findByEmail(dto.getUserToEmail());
            message.setTo(receiver);

            sender.getSentMessages().add(message);
            receiver.getRecieved().add(message);

            messageService.save(message);
            userService.save(sender);
            userService.save(receiver);

            LOGGER.info("User email: {} has sent the message to user email: {} successfully", p.getName(), dto.getUserToEmail());
            return ResponseEntity.status(200).build();

        }catch (Exception e){
            LOGGER.info("User email: {} has sent the message to user email: {} with failure. Cause: {}", p.getName(), dto.getUserToEmail(), e.getMessage());
        }
        return ResponseEntity.status(400).build();
    }
}
