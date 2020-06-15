package com.rent_a_car.agentski_bekend.service;

import com.rent_a_car.agentski_bekend.model.User;
import com.rent_a_car.agentski_bekend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

//    @Autowired
//    private AuthenticationManager authenticationManager;

    BCryptPasswordEncoder encoder = passwordEncoder();

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email);

        if(user == null){
            throw new UsernameNotFoundException(String.format("No user found with email " + email));
        }

        //return user;
        return new org.springframework.security.core.userdetails.User(user.getEmail(),encoder.encode(user.getPassword()),getGrantedAuthorities(user));
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private Collection<GrantedAuthority> getGrantedAuthorities(User user){

        Collection<GrantedAuthority> grantedAuthority = new ArrayList<>();
        if(user.getRola().getName().equals("admin")){
            grantedAuthority.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
        if(user.getRola().getName().equals("secure_admin")){
            grantedAuthority.add(new SimpleGrantedAuthority("ROLE_SECURE_ADMIN"));
        }
        if(user.getRola().getName().equals("agent")){
            grantedAuthority.add(new SimpleGrantedAuthority("ROLE_AGENT"));
        }
        grantedAuthority.add(new SimpleGrantedAuthority("ROLE_USER"));
        return grantedAuthority;
    }
}
