package com.rent_a_car.agentski_bekend.service;

import com.rent_a_car.agentski_bekend.model.Privilege;
import com.rent_a_car.agentski_bekend.model.Role;
import com.rent_a_car.agentski_bekend.repository.PrivilegeRepository;
import com.rent_a_car.agentski_bekend.repository.RoleRepository;
import com.rent_a_car.agentski_bekend.service.interfaces.PrivilegeServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PrivilegeService implements PrivilegeServiceInterface {

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Override
    public List<Privilege> findByName(String name) {
        Privilege privilege = privilegeRepository.findByName(name);
        List<Privilege> rList = new ArrayList<>();
        rList.add(privilege);
        return rList;
    }

    @Override
    public List<Privilege> findById(Integer id) {
        return null;
    }

}
