package com.rent_a_car.agentski_bekend.service.interfaces;

import com.rent_a_car.agentski_bekend.model.Privilege;
import com.rent_a_car.agentski_bekend.model.Role;

import java.util.List;

public interface PrivilegeServiceInterface {

    List<Privilege> findByName (String name);
    List<Privilege> findById (Integer id);
}
