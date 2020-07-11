package com.ad_service.service.interfaces;


import com.ad_service.model.Cars;
import com.ad_service.model.RentRequest;

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
