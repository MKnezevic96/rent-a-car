package com.admin_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRequestRepository extends JpaRepository<UserRequest, Integer> {
    UserRequest findByEmail(String email);
}
