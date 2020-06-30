package com.ad_service.service.interfaces;



import com.ad_service.model.CarModels;

import java.util.List;

public interface CarModelsServiceInterface {
    CarModels findByName(String name);
    CarModels save(CarModels carModels);
    List<CarModels> findAll();
}
