package com.rent_a_car.agentski_bekend.service;

import com.rent_a_car.agentski_bekend.model.UserRequest;
import com.rent_a_car.agentski_bekend.repository.UserRepository;
import com.rent_a_car.agentski_bekend.repository.UserRequestRepository;
import com.rent_a_car.agentski_bekend.service.interfaces.UserRequestServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserRequestService  implements UserRequestServiceInterface {
    @Autowired
    private UserRequestRepository userRequestRepository;


    @Override
    public UserRequest findById(Integer id) {
        return userRequestRepository.findById(id).orElse(null);
    }

    @Override
    public UserRequest findByEmail(String email) {
        return userRequestRepository.findByEmail(email);
    }

    @Override
    public UserRequest save(UserRequest user) {
        return userRequestRepository.save(user);
    }

    @Override
    public List<UserRequest> findAll() {
        List<UserRequest> result = userRequestRepository.findAll();
        return result;
    }
}
