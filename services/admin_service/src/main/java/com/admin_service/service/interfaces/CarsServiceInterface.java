package com.admin_service.service.interfaces;

import com.admin_service.model.Cars;

import java.util.List;

public interface CarsServiceInterface {

    Cars findByName (String name);
    Cars save(Cars car);
    List<Cars> findAll();
}
