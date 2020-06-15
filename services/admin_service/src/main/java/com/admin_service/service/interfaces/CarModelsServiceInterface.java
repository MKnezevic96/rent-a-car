package com.rent_a_car.agentski_bekend.service.interfaces;

import com.rent_a_car.agentski_bekend.model.CarClass;
import com.rent_a_car.agentski_bekend.model.CarModels;

import java.util.List;

public interface CarModelsServiceInterface {
    CarModels findByName(String name);
    CarModels save(CarModels carModels);
    List<CarModels> findAll();
}
