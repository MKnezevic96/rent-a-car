package com.auth_service.service.interfaces;

import com.auth_service.model.RentRequest;
import com.auth_service.model.User;

import java.util.List;
import java.util.Optional;

public interface UserServiceInterface {


    Optional<User> findById(Integer id);

    User findByEmail(String email);

    User save(User user);

    List<User> findAll();

    void delete(User user);

    User findUserById(Integer id);

    List<RentRequest> findUsersRentRequests(String email);

}
