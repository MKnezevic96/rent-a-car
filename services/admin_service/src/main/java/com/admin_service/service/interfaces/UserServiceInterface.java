package com.rent_a_car.agentski_bekend.service.interfaces;

import com.rent_a_car.agentski_bekend.model.User;

import java.util.List;
import java.util.Optional;


public interface UserServiceInterface {

    public Optional<User> findById(Integer id);
    public User findByEmail(String email);

    public User save(User user);
    public List<User> findAll();

    public void delete(User user);
}
