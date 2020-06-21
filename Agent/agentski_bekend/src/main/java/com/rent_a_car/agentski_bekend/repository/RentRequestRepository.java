package com.rent_a_car.agentski_bekend.repository;
import com.rent_a_car.agentski_bekend.model.RentRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RentRequestRepository extends JpaRepository<RentRequest, Integer> {
    Optional<RentRequest> findById (Integer id);


}
