package com.admin_service.service;

import com.admin_service.model.CarModels;
import com.admin_service.repository.CarModelsRepository;
import com.admin_service.service.interfaces.CarModelsServiceInterface;
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
