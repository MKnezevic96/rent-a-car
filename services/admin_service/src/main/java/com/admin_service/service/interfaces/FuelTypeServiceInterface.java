package com.admin_service.service.interfaces;

import com.admin_service.model.FuelType;

import java.util.List;

public interface FuelTypeServiceInterface {
    FuelType findByName(String name);
    FuelType save(FuelType fuelType);
    List<FuelType> findAll();
}

