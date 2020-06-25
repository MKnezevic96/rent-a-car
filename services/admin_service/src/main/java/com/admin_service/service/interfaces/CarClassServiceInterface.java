package com.admin_service.service.interfaces;

import com.admin_service.model.CarClass;

import java.util.List;

public interface CarClassServiceInterface {

    CarClass findByName(String name);
    CarClass save(CarClass carClass);
    List<CarClass> findAll();
}
