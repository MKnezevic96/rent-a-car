package com.rent_a_car.agentski_bekend.controller;

import com.rent_a_car.agentski_bekend.dto.UserDTO;
import com.rent_a_car.agentski_bekend.model.UserRequest;
import com.rent_a_car.agentski_bekend.model.UserTokenState;
import com.rent_a_car.agentski_bekend.security.TokenUtils;
import com.rent_a_car.agentski_bekend.service.MailService;
import com.rent_a_car.agentski_bekend.service.UserService;
import com.rent_a_car.agentski_bekend.service.interfaces.UserRequestServiceInterface;
import com.sun.xml.messaging.saaj.packaging.mime.MessagingException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
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

import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.Random;

//import com.webencyclop.demo.model.User; TODO import user

@RestController
public class AuthenticationController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRequestServiceInterface userRequestService;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private MailService mailService;

    private static final Logger LOGGER = LogManager.getLogger(RentingController.class.getName());



    @PostMapping(value = "/api/register")
    public ResponseEntity<?> register( @Valid @RequestBody UserDTO dto) {    // pokrece constraint iz dto klaase
        UserRequest user = new UserRequest();
        user.setFirstname(dto.getFirstname());
        user.setLastname(dto.getLastname());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());

        if (dto.getIsSelected().equals("isCompany")) {
            user.setCompany(true);
        } else if (dto.getIsSelected().equals("isUser")) {
            user.setUser(true);
        }

        user.setName(dto.getName());
        user.setAddress(dto.getAdress());
        user.setNumber(dto.getNumber());

        if (!dto.getEmail().matches("[a-zA-Z0-9.']+@(gmail.com)|(yahoo.com)|(uns.ac.rs)")) {
            LOGGER.warn("User registration failed. Cause: invalid email characters");
            return ResponseEntity.status(400).build();
        }

        try {
            userRequestService.save(user);
            LOGGER.info("User: {} registered successfuly.", dto.getEmail());
        } catch (Exception e) {
            LOGGER.warn("User registration failed. Cause: invalid password input");
            return new ResponseEntity<>("Invalid pass", HttpStatus.BAD_REQUEST);

        }
            return ResponseEntity.ok().build();
        }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("home"); // resources/template/home.html
        return modelAndView;
    }


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
            LOGGER.warn("Action create authentication token failed. User account: {} is not activated.", user.getEmail());
            return ResponseEntity.status(403).build();
        }

        String jwt = tokenUtils.generateToken(user.getEmail());
        int expiresIn = tokenUtils.getExpiredId();

        LOGGER.info("Action create authentication token successful for user account: {}. User logged in.", user.getEmail());
        return ResponseEntity.ok(new UserTokenState(jwt, expiresIn, role));

    }


    @RequestMapping(value = "/role", method = RequestMethod.GET)
    public ResponseEntity<?> getRole(Principal p){

        User user = userService.findByEmail(p.getName());

        Collection<?> auth = user.getAuthorities();

        if(auth.size() == 0){
            LOGGER.warn("User: {} has no authorities", p.getName());
            return ResponseEntity.status(500).build();
        }

        LOGGER.info("Action get role by user: {} successful", p.getName());
        return ResponseEntity.ok(auth);
    }


    @RequestMapping(value = "/izadji", method = RequestMethod.GET)
    public ResponseEntity<?> logout(HttpServletRequest request) throws ServletException {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        request.logout();

        LOGGER.info("User: {} logged out successfully", user.getEmail());
        return ResponseEntity.ok().build();
    }


    @PreAuthorize("hasAuthority('acc_menagement')")
    @PostMapping("/changePassword")
    public ResponseEntity<?> changePassword(@RequestBody String newPassword, Principal p){

        try {

            User user = userService.findByEmail(p.getName());

            user.setPassword(passwordEncoder.encode(newPassword));

            userService.save(user);

            LOGGER.info("User: {} changed password successfully", p.getName());
            return ResponseEntity.ok().build();

        } catch (Exception e) {
            LOGGER.error("User: {} failed to change password. Cause:{}", p.getName(), e.getMessage());
        }

        return ResponseEntity.status(400).build();

    }


    @PreAuthorize("hasAuthority('acc_menagement')")
    @PostMapping("/checkPassword")
    public ResponseEntity<?> checkPassword(@RequestBody String oldPassword, Principal p){

        User user = userService.findByEmail(p.getName());

//        String pass = user.getPassword();
//        String ppass = passwordEncoder.encode(oldPassword);

        if( user.getPassword().equals(passwordEncoder.encode(oldPassword))) {
            LOGGER.info("Password check by user: {} successful", p.getName());
            return ResponseEntity.ok().build();
        }

        if( passwordEncoder.matches(oldPassword, user.getPassword())) {
            LOGGER.info("Password check by user: {} successful", p.getName());
            return ResponseEntity.ok().build();
        }

        LOGGER.warn("Password check by user: {} failed", p.getName());
        return ResponseEntity.status(402).build();
    }


    @PostMapping("/recoverEmail")
    public ResponseEntity<?> forgotPassword(@RequestBody String email){

        User user = userService.findByEmail(email);
        String newPassword = generatePassword();
        user.setPassword(passwordEncoder.encode(newPassword));

        String text = "Dear sir/madam, " + '\n';
        text += "we have been informed about your request for account recovery. \n Please use your new password "+ newPassword + " " +
                "for your next login and dont forget to set new password of your liking";

        try {

            mailService.sendEmail(user.getEmail(), text, "Account recovery");
            LOGGER.error("action=recover email, user={}, result=success", email);

        } catch (Exception e) {
            LOGGER.error("action=recover email, user={}, result=failure, cause={}", email, e.getMessage());
        }

        userService.save(user);

        return ResponseEntity.ok().build();
    }



    public String generatePassword(){
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int leftLimitCapital = 65; // letter 'a'
        int rightLimitCapital = 90; // letter 'z'
        int leftLimitNumber = 48; // letter 'a'
        int rightLimitNumber = 57; // letter 'z'
        int leftLimitSpec = 41; // letter 'a'
        int rightLimitSpec = 46; // letter 'z'
        int length = 4;
        int lengthNumber = 2;
        int lengthSpec = 1;

        Random random = new Random();
        //mala slova
        StringBuilder buffer = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        String generatedString1 = buffer.toString();

        //velika
        StringBuilder bufferr = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomLimitedInt = leftLimitCapital + (int)
                    (random.nextFloat() * (rightLimitCapital - leftLimitCapital + 1));
            bufferr.append((char) randomLimitedInt);
        }
        String generatedString2 = bufferr.toString();

        //broj
        StringBuilder bufferrr = new StringBuilder(lengthNumber);
        for (int i = 0; i < lengthNumber; i++) {
            int randomLimitedInt = leftLimitNumber + (int)
                    (random.nextFloat() * (rightLimitNumber - leftLimitNumber + 1));
            bufferrr.append((char) randomLimitedInt);
        }
        String generatedString3 = bufferrr.toString();

        //spec
        StringBuilder bufferrrr = new StringBuilder(lengthSpec);
        for (int i = 0; i < lengthSpec; i++) {
            int randomLimitedInt = leftLimitSpec + (int)
                    (random.nextFloat() * (rightLimitSpec - leftLimitSpec + 1));
            bufferrrr.append((char) randomLimitedInt);
        }
        String generatedString4 = bufferrrr.toString();

        return generatedString1+generatedString3+"!"+generatedString2;

    }
}

