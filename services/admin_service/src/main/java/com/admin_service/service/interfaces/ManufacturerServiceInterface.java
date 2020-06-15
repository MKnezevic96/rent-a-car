package com.rent_a_car.agentski_bekend.service.interfaces;

import com.rent_a_car.agentski_bekend.model.Manufacturer;

import java.util.List;

public interface ManufacturerServiceInterface {
    Manufacturer findByName(String name);
    Manufacturer save(Manufacturer manufacturer);
    List<Manufacturer> findAll();
}
