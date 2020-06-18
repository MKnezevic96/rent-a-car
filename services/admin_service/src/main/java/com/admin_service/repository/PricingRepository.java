package com.admin_service.repository;

import com.admin_service.model.Pricing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PricingRepository extends JpaRepository<Pricing, Integer> {
    Pricing findByName(String name);


}
