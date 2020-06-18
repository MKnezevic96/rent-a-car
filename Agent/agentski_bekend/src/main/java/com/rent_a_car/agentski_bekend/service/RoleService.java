package com.rent_a_car.agentski_bekend.service;

import com.rent_a_car.agentski_bekend.model.Role;
import com.rent_a_car.agentski_bekend.repository.RoleRepository;
import com.rent_a_car.agentski_bekend.service.interfaces.RoleServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
