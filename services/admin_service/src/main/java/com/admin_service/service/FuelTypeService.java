package com.admin_service.service;

import com.admin_service.model.FuelType;
import com.admin_service.repository.FuelTypeRepository;
import com.admin_service.service.interfaces.FuelTypeServiceInterface;
import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class FuelTypeService implements FuelTypeServiceInterface {

    @Autowired
    private FuelTypeRepository fuelTypeRepository;

    @Override
    public FuelType findByName(String name) {
        return fuelTypeRepository.findByName(name);
    }

    @Override
    public FuelType save(FuelType fuelType) {
        return fuelTypeRepository.save(fuelType);
    }

    @Override
    public List<FuelType> findAll() {
        return fuelTypeRepository.findAll();
    }
}
