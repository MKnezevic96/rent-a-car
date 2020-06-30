package com.renting_service.service;

import java.util.List;
import com.renting_service.model.UserRequest;
import com.renting_service.repository.UserRequestRepository;
import com.renting_service.service.interfaces.UserRequestServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRequestService implements UserRequestServiceInterface {
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

    @Override
    public void delete(UserRequest userRequest) {
        userRequestRepository.delete(userRequest);
    }
}
