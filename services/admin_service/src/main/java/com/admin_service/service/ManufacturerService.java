package com.rent_a_car.agentski_bekend.service;

import com.rent_a_car.agentski_bekend.model.Manufacturer;
import com.rent_a_car.agentski_bekend.repository.ManufacturerRepository;
import com.rent_a_car.agentski_bekend.service.interfaces.ManufacturerServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
