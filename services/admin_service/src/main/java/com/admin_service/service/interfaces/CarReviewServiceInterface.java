package com.admin_service.service.interfaces;

import com.admin_service.model.CarReview;

import java.util.List;
import java.util.Optional;

public interface CarReviewServiceInterface {

    Optional<CarReview> findById (Integer id);
    CarReview save(CarReview carReview);
    List<CarReview> findAll();
}