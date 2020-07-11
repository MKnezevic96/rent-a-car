package com.admin_service.service.interfaces;

import com.admin_service.model.Cars;
import com.admin_service.model.RentRequest;

import java.util.List;

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
