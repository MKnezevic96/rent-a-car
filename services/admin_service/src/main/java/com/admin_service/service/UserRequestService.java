package com.admin_service.service;

import com.admin_service.model.UserRequest;
import com.admin_service.repository.UserRequestRepository;
import com.admin_service.service.interfaces.UserRequestServiceInterface;
import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

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
