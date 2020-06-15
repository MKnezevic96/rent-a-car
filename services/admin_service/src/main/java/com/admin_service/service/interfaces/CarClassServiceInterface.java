package com.admin_service.service.interfaces;

import java.util.List;
import com.admin_service.model.CarClass;

public interface CarClassServiceInterface {
    CarClass findByName(String name);
    CarClass save(CarClass carClass);
    List<CarClass> findAll();
}
