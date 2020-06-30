package com.admin_service.service;

import com.admin_service.model.Manufacturer;
import com.admin_service.repository.ManufacturerRepository;
import com.admin_service.service.interfaces.ManufacturerServiceInterface;
import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class ManufacturerService implements ManufacturerServiceInterface {

    @Autowired
    ManufacturerRepository manufacturerRepository;

    @Override
    public Manufacturer findByName(String name) {
        return manufacturerRepository.findByName(name);
    }

    @Override
    public Manufacturer save(Manufacturer manufacturer) {
        return manufacturerRepository.save(manufacturer);
    }

    @Override
    public List<Manufacturer> findAll() {
        return manufacturerRepository.findAll();
    }

}
