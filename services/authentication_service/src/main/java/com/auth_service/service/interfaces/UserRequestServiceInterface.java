package com.auth_service.service.interfaces;

import com.auth_service.model.UserRequest;

import java.util.List;

public interface UserRequestServiceInterface {
    public UserRequest findById(Integer id);
    public UserRequest findByEmail(String email);

    public UserRequest save(UserRequest user) throws Exception;
    public List<UserRequest> findAll();
    public void delete(UserRequest userRequest);
}

