package com.admin_service.repository;

import com.admin_service.model.TransmissionType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransmissionTypeRepository extends JpaRepository<TransmissionType, Integer> {
    TransmissionType findByName(String name);
}