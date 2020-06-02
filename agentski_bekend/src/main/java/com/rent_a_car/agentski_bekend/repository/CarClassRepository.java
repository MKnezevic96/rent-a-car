package com.rent_a_car.agentski_bekend.repository;

import com.rent_a_car.agentski_bekend.model.CarClass;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarClassRepository extends JpaRepository<CarClass, Integer> {
    CarClass findByName(String name);
}
