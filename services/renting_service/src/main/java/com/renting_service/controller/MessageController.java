package com.renting_service.controller;

import com.renting_service.dto.MessageDTO;
import com.renting_service.dto.UserDTO;
import com.renting_service.model.Message;
import com.renting_service.model.RentRequest;
import com.renting_service.model.User;
import com.renting_service.model.enums.RequestStatus;
import com.renting_service.service.MailService;
import com.renting_service.service.MessageService;
import com.renting_service.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.MediaType;
import java.security.Principal;
import java.util.*;

@RestController
public class MessageController {

    private static final Logger LOGGER = LogManager.getLogger(RentingController.class.getName());

    @Autowired
    MessageService messageService;

    @Autowired
    UserService userService;

    @Autowired
    MailService mailService;



    @PreAuthorize("hasAuthority('msg_menagement_read')")
    @GetMapping(value = "users")
    public ResponseEntity<List<UserDTO>> getAllUsers (@RequestParam(value = "param", required = false) String param, Principal p) {
        List<UserDTO> retVal = new ArrayList<>();
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
            } else if (param.equals("to")) {
                for (User u : messageService.findAllToUsers(p.getName())) {
                    emails.add(u.getEmail());
                }
            } else if (param.equals("from")) {
                for (User u : messageService.findAllFromUsers(p.getName())) {
                    emails.add(u.getEmail());
                }
            }

            for(RentRequest rr : currentUser.getRentRequests() ){
                if(!rr.getStatus().equals(RequestStatus.PENDING))
                    emails.add(rr.getCarId().getOwner().getEmail());
            }

            Set<String> set = new HashSet<>(emails);
            emails.clear();
            emails.addAll(set);

            for(String email:emails){
                retVal.add(new UserDTO(userService.findByEmail(email)));
            }

            LOGGER.info("action=get users, user={}, result=success", p.getName());
            return new ResponseEntity<>(retVal, HttpStatus.OK);

        } catch(Exception e){
            LOGGER.error("action=get users, user={}, result=failure, cause={}", p.getName(), e.getMessage());
        }

        return new ResponseEntity<>(retVal, HttpStatus.BAD_REQUEST);
    }


    @PreAuthorize("hasAuthority('msg_menagement_read')")
    @GetMapping(value = "history")
    public ResponseEntity<List<MessageDTO>> getMessageHistory (@RequestParam(value = "email", required = false) String email,  Principal p) {

        List<MessageDTO> retVal = new ArrayList<>();

        try{

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

            LOGGER.info("action=get message history, user={}, result=success", p.getName());
            return new ResponseEntity<>(retVal, HttpStatus.OK);

        } catch (Exception e){
            LOGGER.error("action=get message history, user={}, result=failure, cause={}", p.getName(), e.getMessage());
        }
        return ResponseEntity.status(400).build();

    }


    @PreAuthorize("hasAuthority('msg_menagement_write')")
    @PostMapping(value="message", consumes = MediaType.APPLICATION_JSON)
    public ResponseEntity<?> sendMessage(@RequestBody MessageDTO dto, Principal p){


        User user = userService.findByEmail(p.getName());
        if(user.isAdBan()){
            return ResponseEntity.status(403).build();
        }

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

            String text = "Dear sir/madam, " + '\n';
            text += "you have received a new message from user " + p.getName() + ".";
            text +=  "\n\n\n" + "Sincerely, Rent a car support team.";
            mailService.sendEmail(dto.getUserToEmail(), "New message received!", text);

            messageService.save(message);
            userService.save(sender);
            userService.save(receiver);

            LOGGER.info("action=send message, user={}, result=success", p.getName());
            return ResponseEntity.status(200).build();

        }catch (Exception e){
            LOGGER.error("action=send message, user={}, result=failure, cause={}", p.getName(), e.getMessage());
        }
        return ResponseEntity.status(400).build();
    }



}
