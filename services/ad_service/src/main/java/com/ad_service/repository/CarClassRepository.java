package com.ad_service.repository;

import com.ad_service.model.CarClass;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarClassRepository extends JpaRepository<CarClass, Integer> {
    CarClass findByName(String name);
}
