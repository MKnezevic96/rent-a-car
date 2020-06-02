package com.rent_a_car.agentski_bekend.repository;

import com.rent_a_car.agentski_bekend.model.Manufacturer;
import com.rent_a_car.agentski_bekend.model.Pricing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PricingRepository extends JpaRepository<Pricing, Integer> {
    Pricing findByName(String name);


}
