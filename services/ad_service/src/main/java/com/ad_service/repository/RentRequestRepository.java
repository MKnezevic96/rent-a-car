package com.ad_service.repository;
import com.ad_service.model.RentRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RentRequestRepository extends JpaRepository<RentRequest, Integer> {
    Optional<RentRequest> findById (Integer id);


}
