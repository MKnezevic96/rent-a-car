package com.rent_a_car.agentski_bekend.service.interfaces;

import com.rent_a_car.agentski_bekend.model.Role;

import java.util.List;

public interface RoleServiceInterface {

    List<Role> findByName (String name);
    List<Role> findById (Integer id);
}
