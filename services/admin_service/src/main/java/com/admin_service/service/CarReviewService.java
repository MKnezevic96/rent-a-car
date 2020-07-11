package com.admin_service.service;

import com.admin_service.model.CarReview;
import com.admin_service.repository.CarReviewRepository;
import com.admin_service.service.interfaces.CarReviewServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarReviewService implements CarReviewServiceInterface {

    @Autowired
    private CarReviewRepository carReviewRepository;

    @Override
    public Optional<CarReview> findById(Integer id) {
        return carReviewRepository.findById(id);
    }

    @Override
    public CarReview save(CarReview carReview) {
        return carReviewRepository.save(carReview);
    }

    @Override
    public List<CarReview> findAll() {
        return carReviewRepository.findAll();
    }
}
