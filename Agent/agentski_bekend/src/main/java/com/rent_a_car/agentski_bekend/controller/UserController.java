package com.rent_a_car.agentski_bekend.controller;

import com.rent_a_car.agentski_bekend.dto.UserDTO;
import com.rent_a_car.agentski_bekend.model.User;
import com.rent_a_car.agentski_bekend.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.owasp.encoder.Encode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


public class UserController {

    private static final Logger LOGGER = LogManager.getLogger(RentingController.class.getName());


    @PostMapping(value = "/user")
    @ResponseBody
    public ResponseEntity<Object> saveUser(@Valid User user,
                                           BindingResult result, Model model) {
        if (result.hasErrors()) {
            List<String> errors = result.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(errors, HttpStatus.OK);
        } else {
            if (users.stream().anyMatch(it -> user.getEmail().equals(it.getEmail()))) {
                LOGGER.warn("Action create user: {} failed. Cause: Email already exists!");
                return new ResponseEntity<>(
                        Collections.singletonList("Email already exists!"),
                        HttpStatus.CONFLICT);
            } else {

                try {
                    users.add(user);
                    LOGGER.info("User: {} created successfully", user.getEmail());
                    return new ResponseEntity<>(HttpStatus.CREATED);
                } catch (Exception e) {
                    LOGGER.error("Action create user: {} failed. Cause: {}", user.getEmail(), e.getMessage());
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

                }

            }
        }
    }

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private List<User> users = Arrays.asList();


        //kada se uloguje
    @GetMapping("/userPage")
    public String getUserProfilePage() {
        return "user";
    }


}
