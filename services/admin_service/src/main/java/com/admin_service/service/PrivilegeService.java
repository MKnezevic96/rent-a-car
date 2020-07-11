package com.admin_service.service;

import com.admin_service.model.Privilege;
import com.admin_service.repository.PrivilegeRepository;
import com.admin_service.service.interfaces.PrivilegeServiceInterface;
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
