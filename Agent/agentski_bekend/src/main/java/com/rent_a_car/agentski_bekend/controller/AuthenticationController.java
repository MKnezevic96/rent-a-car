package com.rent_a_car.agentski_bekend.controller;

import com.rent_a_car.agentski_bekend.dto.UserDTO;
import com.rent_a_car.agentski_bekend.model.UserRequest;
import com.rent_a_car.agentski_bekend.model.UserTokenState;
import com.rent_a_car.agentski_bekend.security.TokenUtils;
import com.rent_a_car.agentski_bekend.service.UserService;
import com.rent_a_car.agentski_bekend.service.interfaces.UserRequestServiceInterface;
import org.springframework.security.access.prepost.PreAuthorize;
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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.SecurityContext;
import java.security.Principal;
import java.util.Collection;
import java.util.List;

//import com.webencyclop.demo.model.User; TODO import user

@RestController
public class AuthenticationController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRequestServiceInterface userRequestService;

//    @PostMapping(value ="/api/login")
//    public ResponseEntity<?> login(@RequestBody JwtAuthenticationRequest authenticationRequest) {
//        try{
//            User user = userService.findByEmail(authenticationRequest.getEmail());
//            if(user.getPassword().equals(authenticationRequest.getPassword())) {
//                if(user.isActivated()) {
//                    return ResponseEntity.ok().build();
//                }else{
//                    return ResponseEntity.status(403).build();
//                }
//            }
//            return ResponseEntity.status(401).build();
//
//        }catch (Exception e){
//
//            };
//        return ResponseEntity.status(401).build();
//
//
//    }

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
          else if(dto.getIsSelected().equals("isUser")) {
              user.setUser(true);
          }
          user.setName(dto.getName());
          user.setAddress(dto.getAdress());
          user.setNumber(dto.getNumber());
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

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenUtils tokenUtils;
    @PostMapping(value = "/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest){

//        final Authentication authentication = authenticationManager
//                .authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(),
//                        authenticationRequest.getPassword()));

        UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(),
                        authenticationRequest.getPassword());

        final Authentication authentication = authenticationManager
                .authenticate(upat);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String role = new String();//
        //role = authentication.getAuthorities().iterator().next().getAuthority();
        User user = (User)customUserDetailsService.loadUserByUsername(authenticationRequest.getEmail());
        role = user.getRole().iterator().next().getName();
        if(!user.isActivated()){
            return ResponseEntity.status(403).build();
        }

        String jwt = tokenUtils.generateToken(user.getEmail());
        int expiresIn = tokenUtils.getExpiredId();

        return ResponseEntity.ok(new UserTokenState(jwt, expiresIn, role));

    }

    @RequestMapping(value = "/role", method = RequestMethod.GET)
    public ResponseEntity<?> getRole(Principal p){

        User user = userService.findByEmail(p.getName());

        Collection<?> auth = user.getAuthorities();

        if(auth.size() == 0){
            return ResponseEntity.status(500).build();
        }

        return ResponseEntity.ok(auth);
    }

    @RequestMapping(value = "/izadji", method = RequestMethod.GET)
    public ResponseEntity<?> logout(HttpServletRequest request) throws ServletException {
        request.logout();

        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAuthority('acc_menagement')")
    @PostMapping("/changePassword")
    public ResponseEntity<?> changePassword(@RequestBody String newPassword, Principal p){

        User user = userService.findByEmail(p.getName());

        user.setPassword(passwordEncoder.encode(newPassword));

        userService.save(user);

        return ResponseEntity.ok().build();

    }
    @PreAuthorize("hasAuthority('acc_menagement')")
    @PostMapping("/checkPassword")
    public ResponseEntity<?> checkPassword(@RequestBody String oldPassword, Principal p){

        User user = userService.findByEmail(p.getName());

//        String pass = user.getPassword();
//        String ppass = passwordEncoder.encode(oldPassword);

        if( user.getPassword().equals(passwordEncoder.encode(oldPassword))) {
            return ResponseEntity.ok().build();
        }

        if( passwordEncoder.matches(oldPassword, user.getPassword())) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.status(402).build();


    }

}

