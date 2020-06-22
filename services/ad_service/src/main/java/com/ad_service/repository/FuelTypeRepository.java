package com.ad_service.repository;

import com.ad_service.model.FuelType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FuelTypeRepository extends JpaRepository<FuelType, Integer> {
    FuelType findByName(String name);
}
