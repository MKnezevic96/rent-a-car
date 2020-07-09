package com.rent_a_car.agentski_bekend.service;
import com.rent_a_car.agentski_bekend.model.Message;
import com.rent_a_car.agentski_bekend.model.User;
import com.rent_a_car.agentski_bekend.repository.MessageRepository;
import com.rent_a_car.agentski_bekend.service.interfaces.MessageServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class MessageService implements MessageServiceInterface {

    @Autowired
    private UserService userService;

    @Autowired
    private MessageRepository messageRepository;


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

}
