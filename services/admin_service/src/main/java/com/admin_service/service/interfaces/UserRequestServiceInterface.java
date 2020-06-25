package com.admin_service.service.interfaces;

import com.admin_service.model.UserRequest;

import java.util.List;

public interface UserRequestServiceInterface {
    UserRequest findById(Integer id);
    UserRequest findByEmail(String email);

    UserRequest save(UserRequest user);
    List<UserRequest> findAll();
    void delete(UserRequest userRequest);
}
