package com.rent_a_car.agentski_bekend.service;

import com.rent_a_car.agentski_bekend.model.Cars;
import com.rent_a_car.agentski_bekend.repository.CarsRepository;
import com.rent_a_car.agentski_bekend.service.interfaces.CarsServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarsService implements CarsServiceInterface {
    @Autowired
    private CarsRepository carModelsRepository;

  //  @Override
 //   public Cars findByName(String name) {
  //      return carModelsRepository.findByName(name);
  //  }

    @Override
    public Cars findByName(String name) {
        return carModelsRepository.findByName(name);
    }

    @Override
    public Cars save(Cars carModels) {
        return carModelsRepository.save(carModels);
    }

    @Override
    public List<Cars> findAll() {
        return carModelsRepository.findAll();
    }
}
