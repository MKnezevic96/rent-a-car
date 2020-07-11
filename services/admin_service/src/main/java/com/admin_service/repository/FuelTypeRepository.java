package com.admin_service.repository;

import com.admin_service.model.FuelType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FuelTypeRepository extends JpaRepository<FuelType, Integer> {
    FuelType findByName(String name);
}
