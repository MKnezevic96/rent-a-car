package com.ad_service.service;


import com.ad_service.model.FuelType;
import com.ad_service.repository.FuelTypeRepository;
import com.ad_service.service.interfaces.FuelTypeServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
