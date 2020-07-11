package com.auth_service.repository;

import com.auth_service.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    //    @Query
//    User findById(long id);
//    @Query
    User findByEmail(String email);
}
