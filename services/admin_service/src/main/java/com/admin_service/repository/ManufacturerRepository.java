package com.admin_service.repository;

import com.admin_service.model.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManufacturerRepository extends JpaRepository<Manufacturer, Integer> {
    Manufacturer findByName(String name);
}