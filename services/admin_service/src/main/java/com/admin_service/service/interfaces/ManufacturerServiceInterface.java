package com.admin_service.service.interfaces;

import com.admin_service.model.Manufacturer;

import java.util.List;

public interface ManufacturerServiceInterface {
    Manufacturer findByName(String name);
    Manufacturer save(Manufacturer manufacturer);
    List<Manufacturer> findAll();
}
