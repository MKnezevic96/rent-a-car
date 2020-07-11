package com.admin_service.service;

import com.admin_service.model.Role;
import com.admin_service.repository.RoleRepository;
import com.admin_service.service.interfaces.RoleServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleService implements RoleServiceInterface {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<Role> findByName(String name) {
        Role role = roleRepository.findByName(name);
        List<Role> rList = new ArrayList<>();
        rList.add(role);
        return rList;
    }

    @Override
    public List<Role> findById(Integer id) {
//        Role role = roleRepository.findById(id);
//        List<Role> rList = new ArrayList<>();
//        rList.add(role);
//        return rList;
        return null;
    }
}
