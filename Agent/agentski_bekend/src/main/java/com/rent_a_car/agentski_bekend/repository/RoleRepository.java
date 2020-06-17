package com.rent_a_car.agentski_bekend.repository;

import com.rent_a_car.agentski_bekend.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Role findByName (String name);
}
