package com.rent_a_car.agentski_bekend.repository;

import com.rent_a_car.agentski_bekend.model.User;
import com.rent_a_car.agentski_bekend.model.UserRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRequestRepository extends JpaRepository<UserRequest, Integer> {
    UserRequest findByEmail(String email);
}
