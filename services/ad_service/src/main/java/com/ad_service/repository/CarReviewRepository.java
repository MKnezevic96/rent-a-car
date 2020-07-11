package com.ad_service.repository;


import com.ad_service.model.CarReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarReviewRepository extends JpaRepository<CarReview, Integer> {
    Optional<CarReview> findById(Integer id);
}

