package com.rent_a_car.agentski_bekend.service.interfaces;

import com.rent_a_car.agentski_bekend.model.TransmissionType;

import java.util.List;

public interface TransmissionTypeServiceInterface {
    TransmissionType findByName(String name);
    TransmissionType save(TransmissionType transmissionType);
    List<TransmissionType> findAll();
}
