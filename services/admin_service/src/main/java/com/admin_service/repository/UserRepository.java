package com.admin_service.repository;

import com.admin_service.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface UserRepository extends JpaRepository<User, Integer> {
//    @Query
//    User findById(long id);
//    @Query
    User findByEmail(String email);
}
