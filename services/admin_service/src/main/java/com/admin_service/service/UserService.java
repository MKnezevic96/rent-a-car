package com.rent_a_car.agentski_bekend.service;

import com.rent_a_car.agentski_bekend.model.User;
import com.rent_a_car.agentski_bekend.repository.UserRepository;
import com.rent_a_car.agentski_bekend.service.interfaces.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserServiceInterface {

    @Autowired
    private UserRepository userRepository;


    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findById(Integer id) {
        return userRepository.findById(id);
    }



    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> findAll() {
        List<User> result = userRepository.findAll();
        return result;
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }
}
