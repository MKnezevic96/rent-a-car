package com.rent_a_car.agentski_bekend.repository;

import com.rent_a_car.agentski_bekend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findById(long id);
    User findByUsername(String username);
}
