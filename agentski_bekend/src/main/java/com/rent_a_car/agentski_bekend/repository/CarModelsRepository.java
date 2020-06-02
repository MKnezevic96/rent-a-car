package com.rent_a_car.agentski_bekend.repository;

import com.rent_a_car.agentski_bekend.model.CarModels;
import com.rent_a_car.agentski_bekend.model.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarModelsRepository extends JpaRepository<CarModels, Integer> {
    CarModels findByName(String name);
}
