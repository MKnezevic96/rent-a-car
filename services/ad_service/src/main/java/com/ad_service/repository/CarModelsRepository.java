package com.ad_service.repository;


import com.ad_service.model.CarModels;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarModelsRepository extends JpaRepository<CarModels, Integer> {
    CarModels findByName(String name);
}
