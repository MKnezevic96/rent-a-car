package com.rent_a_car.agentski_bekend.controller;

import com.rent_a_car.agentski_bekend.dto.UserDTO;
import com.rent_a_car.agentski_bekend.model.UserRequest;
import com.rent_a_car.agentski_bekend.model.UserTokenState;
import com.rent_a_car.agentski_bekend.security.TokenUtils;
import com.rent_a_car.agentski_bekend.service.UserService;
import com.rent_a_car.agentski_bekend.service.interfaces.UserRequestServiceInterface;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import com.rent_a_car.agentski_bekend.model.User;
import com.rent_a_car.agentski_bekend.security.auth.JwtAuthenticationRequest;
import com.rent_a_car.agentski_bekend.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.ws.rs.core.SecurityContext;
import java.util.List;

//import com.webencyclop.demo.model.User; TODO import user

@RestController
public class AuthenticationController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRequestServiceInterface userRequestService;

    @PostMapping(value ="/api/login")
    public ResponseEntity<?> login(@RequestBody JwtAuthenticationRequest authenticationRequest) {
        try{
            User user = userService.findByEmail(authenticationRequest.getEmail());
            if(user.getPassword().equals(authenticationRequest.getPassword())) {
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.status(400).build();

        }catch (Exception e){

            };
        return ResponseEntity.status(400).build();


    }

    @PostMapping(value = "/api/register")
    public ResponseEntity<?> register(@RequestBody UserDTO dto) {
        UserRequest user = new UserRequest();
        user.setFirstname(dto.getFirstname());
        user.setLastname(dto.getLastname());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());

          if(dto.getIsSelected().equals("isCompany")) {
              user.setCompany(true);
          }
          else if(dto.getIsSelected().equals("isAgent")) {
              user.setAgent(true);
          }
          else if(dto.getIsSelected().equals("isCustomer")) {
              user.setCustomer(true);
          }

        if(!dto.getEmail().matches("[a-zA-Z0-9.']+@(gmail.com)|(yahoo.com)|(uns.ac.rs)")){
            return ResponseEntity.status(400).build();
        }
        userRequestService.save(user);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("home"); // resources/template/home.html
        return modelAndView;
    }

//    @Autowired
//    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

//    @Autowired
//    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenUtils tokenUtils;
    @PostMapping(value = "/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest){

//        final Authentication authentication = authenticationManager
//                .authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(),
//                        authenticationRequest.getPassword()));

        UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(),
                        authenticationRequest.getPassword());

//        final Authentication authentication = authenticationManager
//                .authenticate(upat);
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        User user = (User)customUserDetailsService.loadUserByUsername(authenticationRequest.getEmail());
//
//        String jwt = tokenUtils.generateToken(user.getEmail());
//        int expiresIn = tokenUtils.getExpiredId();
//
//        return ResponseEntity.ok(new UserTokenState(jwt, expiresIn));

        return null;
    }
}

