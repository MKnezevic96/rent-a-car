package com.admin_service.service.interfaces;

import com.admin_service.model.TransmissionType;

import java.util.List;

public interface TransmissionTypeServiceInterface {
    TransmissionType findByName(String name);
    TransmissionType save(TransmissionType transmissionType);
    List<TransmissionType> findAll();
}

