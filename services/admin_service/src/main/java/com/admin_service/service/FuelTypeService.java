package com.rent_a_car.agentski_bekend.service;

import com.rent_a_car.agentski_bekend.model.FuelType;
import com.rent_a_car.agentski_bekend.repository.FuelTypeRepository;
import com.rent_a_car.agentski_bekend.service.interfaces.FuelTypeServiceInterface;
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
