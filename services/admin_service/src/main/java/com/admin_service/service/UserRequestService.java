package com.admin_service.service;

import com.admin_service.model.UserRequest;
import com.admin_service.repository.UserRequestRepository;
import com.admin_service.security.constraint.PasswordConstraintValidator;
import com.admin_service.service.interfaces.UserRequestServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserRequestService  implements UserRequestServiceInterface {
    @Autowired
    private UserRequestRepository userRequestRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private PasswordConstraintValidator passwordConstraintValidator=new PasswordConstraintValidator();


    @Override
    public UserRequest findById(Integer id) {
        return userRequestRepository.findById(id).orElse(null);
    }

    @Override
    public UserRequest findByEmail(String email) {
        return userRequestRepository.findByEmail(email);
    }

    @Override
    public UserRequest save(UserRequest user) throws Exception {

        if(passwordConstraintValidator.isValid(user.getPassword(), null)) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRequestRepository.save(user);
        } else {
            throw new Exception("");
        }


    }

    @Override
    public List<UserRequest> findAll() {
        List<UserRequest> result = userRequestRepository.findAll();
        return result;
    }

    @Override
    public void delete(UserRequest userRequest) {
        userRequestRepository.delete(userRequest);
    }
}
