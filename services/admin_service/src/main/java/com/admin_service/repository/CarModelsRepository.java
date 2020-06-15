package com.admin_service.repository;

import com.admin_service.model.CarModels;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarModelsRepository extends JpaRepository<CarModels, Integer> {
    CarModels findByName(String name);
}
