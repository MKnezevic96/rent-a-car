package com.admin_service.service;

import com.admin_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class InitializeService {

    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    public void init(){

//        Privilege pr = new Privilege();
//        pr.setName("codebook_management");
//        privilegeRepository.save(pr);
//
//        Role r = new Role();
//        r.setName("rola1");
//        r.setPrivileges((pr);
//
//
//        User u = new User();
//        u.setUsername("mika");
//        u.setPassword("antic");
//        userRepository.save(u);
    }
}
