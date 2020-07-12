package com.renting_service.service;

import com.renting_service.model.RentRequest;
import com.renting_service.model.User;
import com.renting_service.repository.UserRepository;
import com.renting_service.service.interfaces.UserServiceInterface;
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
    public List<RentRequest> findUsersRentRequests(String email){
        User user = userRepository.findByEmail(email);
        List<RentRequest> result = user.getRentRequests();
        return result;
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }

    @Override
    public User findUserById(Integer id) {
        List<User> users = userRepository.findAll();
        for(User u : users){
            if(u.getId().equals(id))
                return u;
        }
        return null;
    }
}
