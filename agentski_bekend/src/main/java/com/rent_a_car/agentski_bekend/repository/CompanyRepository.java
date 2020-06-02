package com.rent_a_car.agentski_bekend.repository;

import com.rent_a_car.agentski_bekend.model.Company;

import com.rent_a_car.agentski_bekend.model.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Integer> {
    Company findByName(String name);

}
