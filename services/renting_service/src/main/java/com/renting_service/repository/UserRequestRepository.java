package com.renting_service.repository;

import java.util.List;
import com.renting_service.model.UserRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRequestRepository extends JpaRepository<UserRequest, Integer> {
    UserRequest findByEmail(String email);

    List<UserRequest> findAll();

}