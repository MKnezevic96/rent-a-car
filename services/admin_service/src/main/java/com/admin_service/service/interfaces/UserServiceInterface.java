package com.admin_service.service.interfaces;

import com.admin_service.model.User;

import java.util.List;
import java.util.Optional;

public interface UserServiceInterface {

    public Optional<User> findById(Integer id);
    public User findByEmail(String email);

    public User save(User user);
    public List<User> findAll();

    public void delete(User user);

}
