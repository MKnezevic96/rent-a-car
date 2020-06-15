package com.admin_service.service;

import com.admin_service.model.CarClass;
import com.admin_service.repository.CarClassRepository;
import com.admin_service.service.interfaces.CarClassServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarClassService implements CarClassServiceInterface {

    @Autowired
    private CarClassRepository carClassRepository;

    @Override
    public CarClass findByName(String name) {
        return carClassRepository.findByName(name);
    }

    @Override
    public CarClass save(CarClass carClass) {
        return carClassRepository.save(carClass);
    }

    @Override
    public List<CarClass> findAll() {
        return carClassRepository.findAll();
    }
}
