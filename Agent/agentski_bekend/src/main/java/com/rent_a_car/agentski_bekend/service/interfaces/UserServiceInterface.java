package com.rent_a_car.agentski_bekend.service.interfaces;

import com.rent_a_car.agentski_bekend.model.RentRequest;
import com.rent_a_car.agentski_bekend.model.User;

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
