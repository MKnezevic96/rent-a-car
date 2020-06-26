package com.ad_service.repository;

import com.ad_service.model.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManufacturerRepository extends JpaRepository<Manufacturer, Integer> {
    Manufacturer findByName(String name);
}
