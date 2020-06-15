package com.rent_a_car.agentski_bekend.service;

import com.rent_a_car.agentski_bekend.model.TransmissionType;
import com.rent_a_car.agentski_bekend.repository.TransmissionTypeRepository;
import com.rent_a_car.agentski_bekend.service.interfaces.TransmissionTypeServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransmissionTypeService implements TransmissionTypeServiceInterface {

    @Autowired
    private TransmissionTypeRepository transmissionTypeRepository;

    @Override
    public TransmissionType findByName(String name) {
        return transmissionTypeRepository.findByName(name);
    }

    @Override
    public TransmissionType save(TransmissionType transmissionType) {
        return transmissionTypeRepository.save(transmissionType);
    }

    @Override
    public List<TransmissionType> findAll() {
        return transmissionTypeRepository.findAll();
    }
}
