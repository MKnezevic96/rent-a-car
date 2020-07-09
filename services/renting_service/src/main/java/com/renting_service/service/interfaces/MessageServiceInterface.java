package com.renting_service.service.interfaces;

import com.renting_service.model.Message;
import com.renting_service.model.User;

import java.util.List;

public interface MessageServiceInterface {
    List<User> findAllToUsers(String email);
    List<User> findAllFromUsers(String email);
    List<Message> findAll();
}
