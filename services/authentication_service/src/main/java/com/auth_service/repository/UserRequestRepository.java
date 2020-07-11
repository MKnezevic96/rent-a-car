package com.auth_service.repository;

import com.auth_service.model.UserRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRequestRepository extends JpaRepository<UserRequest, Integer> {
    UserRequest findByEmail(String email);
}

