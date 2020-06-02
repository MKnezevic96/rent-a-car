package com.rent_a_car.agentski_bekend.repository;

import com.rent_a_car.agentski_bekend.model.FuelType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FuelTypeRepository extends JpaRepository<FuelType, Integer> {
    FuelType findByName(String name);
}
