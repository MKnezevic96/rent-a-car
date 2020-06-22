package com.ad_service.service.interfaces;


import com.ad_service.model.Cars;

import java.util.List;
import java.util.Optional;

public interface CarsServiceInterface {

    Cars findByName (String name);
    Cars save(Cars car);
    List<Cars> findAll();
}
