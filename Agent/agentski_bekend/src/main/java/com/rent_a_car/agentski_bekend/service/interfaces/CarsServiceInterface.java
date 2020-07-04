package com.rent_a_car.agentski_bekend.service.interfaces;

import com.rent_a_car.agentski_bekend.model.Cars;
import com.rent_a_car.agentski_bekend.model.RentRequest;

import java.util.List;
import java.util.Optional;

public interface CarsServiceInterface {

    Cars findByName (String name);
    Cars save(Cars car);
    List<Cars> findAll();
    Cars getCar (Integer id);
    void autoReject(RentRequest rentRequestDto);
    boolean checkDates(RentRequest rentRequestDto, RentRequest request);
    String calculateAverageRating(Integer carId);
    String calculateCommentsNumber(Integer carId);
    double calculateMileageNumber(Integer carId);
}
