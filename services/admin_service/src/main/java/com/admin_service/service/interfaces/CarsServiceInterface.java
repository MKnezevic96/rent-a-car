package com.rent_a_car.agentski_bekend.service.interfaces;

import com.rent_a_car.agentski_bekend.model.Cars;

import java.util.List;

public interface CarsServiceInterface {

    Cars findByName (String name);
    Cars save(Cars car);
    List<Cars> findAll();
}
