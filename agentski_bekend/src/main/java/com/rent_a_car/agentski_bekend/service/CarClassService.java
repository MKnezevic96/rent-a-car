package com.rent_a_car.agentski_bekend.service;

import com.rent_a_car.agentski_bekend.model.CarClass;
import com.rent_a_car.agentski_bekend.repository.CarClassRepository;
import com.rent_a_car.agentski_bekend.service.interfaces.CarClassServiceInterface;
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
