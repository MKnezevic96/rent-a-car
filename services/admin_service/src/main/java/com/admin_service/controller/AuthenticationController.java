package com.admin_service.controller;

import com.admin_service.dto.UserDTO;
import com.admin_service.dto.UserRequestDTO;
import com.admin_service.security.auth.JwtAuthenticationRequest;
import com.admin_service.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Collection;


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
            return ResponseEntity.status(401).build();

        }catch (Exception e){

            };
        return ResponseEntity.status(401).build();


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
        role = authentication.getAuthorities().iterator().next().getAuthority();

        User user = (User)customUserDetailsService.loadUserByUsername(authenticationRequest.getEmail());

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

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ResponseEntity<?> logout(HttpServletRequest request) throws ServletException {
        request.logout();

        return ResponseEntity.ok().build();
    }
}

