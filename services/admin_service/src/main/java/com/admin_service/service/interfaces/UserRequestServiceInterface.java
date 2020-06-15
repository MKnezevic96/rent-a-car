package com.rent_a_car.agentski_bekend.service.interfaces;

import com.rent_a_car.agentski_bekend.model.UserRequest;

import java.util.List;

public interface UserRequestServiceInterface {
    public UserRequest findById(Integer id);
    public UserRequest findByEmail(String email);

    public UserRequest save(UserRequest user);
    public List<UserRequest> findAll();
    public void delete(UserRequest userRequest);
}
