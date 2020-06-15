package com.rent_a_car.agentski_bekend.service.interfaces;

import com.rent_a_car.agentski_bekend.model.CarClass;
import com.rent_a_car.agentski_bekend.model.Company;

import java.util.List;

public interface CompanyServiceInterface {

    Company findByName(String name);
    Company save(Company company);
    List<Company> findAll();
}
