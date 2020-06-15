package com.rent_a_car.agentski_bekend.service;

import com.rent_a_car.agentski_bekend.model.CarReview;
import com.rent_a_car.agentski_bekend.model.Cars;
import com.rent_a_car.agentski_bekend.repository.CarReviewRepository;
import com.rent_a_car.agentski_bekend.service.interfaces.CarReviewServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarReviewService implements CarReviewServiceInterface {


    @Autowired
    private CarReviewRepository carReviewRepository;

    @Override
    public CarReview save(CarReview review) {
        return carReviewRepository.save(review);
    }

}

