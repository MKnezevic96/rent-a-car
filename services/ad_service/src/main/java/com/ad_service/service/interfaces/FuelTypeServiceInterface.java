package com.ad_service.service.interfaces;



import com.ad_service.model.FuelType;

import java.util.List;

public interface FuelTypeServiceInterface {
    FuelType findByName(String name);
    FuelType save(FuelType fuelType);
    List<FuelType> findAll();
}
