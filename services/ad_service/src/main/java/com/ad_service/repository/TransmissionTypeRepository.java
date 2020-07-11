package com.ad_service.repository;

import com.ad_service.model.TransmissionType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransmissionTypeRepository extends JpaRepository<TransmissionType, Integer> {
    TransmissionType findByName(String name);
}

