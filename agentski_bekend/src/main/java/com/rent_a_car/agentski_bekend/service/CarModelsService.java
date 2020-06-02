package com.rent_a_car.agentski_bekend.service;

import com.rent_a_car.agentski_bekend.model.CarModels;
import com.rent_a_car.agentski_bekend.repository.CarModelsRepository;
import com.rent_a_car.agentski_bekend.service.interfaces.CarModelsServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarModelsService implements CarModelsServiceInterface {

    @Autowired
    private CarModelsRepository carModelsRepository;

    @Override
    public CarModels findByName(String name) {
        return carModelsRepository.findByName(name);
    }

    @Override
    public CarModels save(CarModels carModels) {
        return carModelsRepository.save(carModels);
    }

    @Override
    public List<CarModels> findAll() {
        return carModelsRepository.findAll();
    }
}
