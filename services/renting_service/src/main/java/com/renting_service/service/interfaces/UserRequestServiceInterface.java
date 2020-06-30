package com.renting_service.service.interfaces;

import java.util.List;

import com.renting_service.model.UserRequest;

public interface UserRequestServiceInterface {
    public UserRequest findById(Integer id);
    public UserRequest findByEmail(String email);

    public UserRequest save(UserRequest user);
    public List<UserRequest> findAll();
    public void delete(UserRequest userRequest);
}
