package com.rent_a_car.agentski_bekend.controller;

import com.rent_a_car.agentski_bekend.model.User;
import com.rent_a_car.agentski_bekend.service.UserService;
import com.rent_a_car.agentski_bekend.service.interfaces.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TestController {

    @Autowired
    private UserService userService;

    @RequestMapping
    public String testService () {
        return "'Ziv sam' - Agentski servis";
    }

    @PostMapping("/add")
    public User saveUser(@RequestBody User user){
        return userService.save(user);
    }

    @GetMapping("/get")
    public List<User> getUsers(){
        return userService.findAll();
    }

}
