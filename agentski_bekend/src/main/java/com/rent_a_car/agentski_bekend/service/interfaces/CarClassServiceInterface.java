package com.rent_a_car.agentski_bekend.service.interfaces;

import com.rent_a_car.agentski_bekend.model.CarClass;

import java.util.List;

public interface CarClassServiceInterface {
    CarClass findByName(String name);
    CarClass save(CarClass carClass);
    List<CarClass> findAll();
}
