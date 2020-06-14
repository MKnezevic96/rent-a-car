package com.rent_a_car.agentski_bekend.service;

import com.rent_a_car.agentski_bekend.model.Cars;
import com.rent_a_car.agentski_bekend.repository.CarsRepository;
import com.rent_a_car.agentski_bekend.service.interfaces.CarsServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CarsService implements CarsServiceInterface {
    @Autowired
    private CarsRepository carsRepository;

  //  @Override
 //   public Cars findByName(String name) {
  //      return carModelsRepository.findByName(name);
  //  }

    @Override
    public Cars findByName(String name) {
        return carsRepository.findByName(name);
    }

    @Override
    public Cars save(Cars carModels) {
        return carsRepository.save(carModels);
    }

    @Override
    public List<Cars> findAll() {
        return carsRepository.findAll();
    }

    public List<Cars> filterByCity (ArrayList<Cars> cars, String town) {
        ArrayList<Cars> retVal = new ArrayList<Cars> ();
        for (Cars car : cars) {
            if (car.getTown().equals(town)) {
                retVal.add(car);
            }
        }
        return retVal;
    }

    public Cars getCar (Integer id) {
        return carsRepository.getOne(id);
    }



}
