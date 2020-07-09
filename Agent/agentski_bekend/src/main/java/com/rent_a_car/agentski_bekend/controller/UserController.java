package com.rent_a_car.agentski_bekend.controller;

import com.rent_a_car.agentski_bekend.dto.UserDTO;
import com.rent_a_car.agentski_bekend.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@RestController
//@RequestMapping(value = "api/users/")
public class UserController {

    private static final Logger LOGGER = LogManager.getLogger(RentingController.class.getName());
    private List<User> users = Arrays.asList();

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

                LOGGER.warn("action=save user, user={}, result=failure, cause=Email already exists", user.getEmail());
                return new ResponseEntity<>(
                        Collections.singletonList("Email already exists!"),
                        HttpStatus.CONFLICT);

            } else {

                try {

                    users.add(user);

                    LOGGER.info("action=save user, user={}, result=success", user.getEmail());
                    return new ResponseEntity<>(HttpStatus.CREATED);

                } catch (Exception e) {

                    LOGGER.error("action=save user, user={}, result=failure, cause={}", user.getEmail(), e.getMessage());
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

                }

            }
        }
    }


    @GetMapping(value="user/current")
    public ResponseEntity<UserDTO> getCurrentUser(){

        try {

            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            UserDTO dto = new UserDTO(user);
            LOGGER.info("action=get current user, user={}, result=success", user.getEmail());
            return new ResponseEntity<>(dto, HttpStatus.OK);

        } catch (Exception e) {
            LOGGER.error("action=get current user, user=, result=failure, cause={}", e.getMessage());
        }

        return ResponseEntity.status(400).build();
    }


        //kada se uloguje
    @GetMapping("/userPage")
    public String getUserProfilePage() {
        return "user";
    }


}
