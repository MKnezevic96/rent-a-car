package com.admin_service.service.interfaces;

import com.admin_service.model.Privilege;

import java.util.List;

public interface PrivilegeServiceInterface {

    List<Privilege> findByName (String name);
    List<Privilege> findById (Integer id);
}