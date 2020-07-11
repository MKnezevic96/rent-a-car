package com.admin_service.service.interfaces;

import com.admin_service.model.Role;

import java.util.List;

public interface RoleServiceInterface {

    List<Role> findByName (String name);
    List<Role> findById (Integer id);
}


