package com.rent_a_car.agentski_bekend.repository;

import com.rent_a_car.agentski_bekend.model.CarReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarReviewRepository extends JpaRepository<CarReview, Integer> {
    Optional<CarReview> findById(Integer id);
}
