package com.rent_a_car.agentski_bekend.repository;

import com.rent_a_car.agentski_bekend.model.Privilege;
import com.rent_a_car.agentski_bekend.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrivilegeRepository extends JpaRepository<Privilege, Integer> {

    Privilege findByName (String name);

}
