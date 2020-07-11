package com.ad_service.service.interfaces;


import com.ad_service.model.Manufacturer;

import java.util.List;

public interface ManufacturerServiceInterface {
    Manufacturer findByName(String name);
    Manufacturer save(Manufacturer manufacturer);
    List<Manufacturer> findAll();
}