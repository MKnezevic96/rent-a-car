package com.rent_a_car.agentski_bekend.service.interfaces;

import com.rent_a_car.agentski_bekend.model.CarReview;

import java.util.List;
import java.util.Optional;

public interface CarReviewServiceInterface {

    Optional<CarReview> findById (Integer id);
    CarReview save(CarReview carReview);
    List<CarReview> findAll();
}
