package com.rent_a_car.agentski_bekend.repository;

import com.rent_a_car.agentski_bekend.model.TransmissionType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransmissionTypeRepository extends JpaRepository<TransmissionType, Integer> {
    TransmissionType findByName(String name);
}
