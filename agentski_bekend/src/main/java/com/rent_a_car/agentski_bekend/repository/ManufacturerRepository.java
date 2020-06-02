package com.rent_a_car.agentski_bekend.repository;

import com.rent_a_car.agentski_bekend.model.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManufacturerRepository extends JpaRepository<Manufacturer, Integer> {
    Manufacturer findByName(String name);
}
