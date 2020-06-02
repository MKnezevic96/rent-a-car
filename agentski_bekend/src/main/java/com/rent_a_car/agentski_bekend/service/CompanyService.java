package com.rent_a_car.agentski_bekend.service;

import com.rent_a_car.agentski_bekend.model.CarModels;
import com.rent_a_car.agentski_bekend.model.Company;
import com.rent_a_car.agentski_bekend.repository.CarModelsRepository;
import com.rent_a_car.agentski_bekend.repository.CompanyRepository;
import com.rent_a_car.agentski_bekend.service.interfaces.CompanyServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService implements CompanyServiceInterface {

    @Autowired
    private CompanyRepository carModelsRepository;

    @Override
    public Company findByName(String name) {
        return carModelsRepository.findByName(name);
    }

    @Override
    public Company save(Company carModels) {
        return carModelsRepository.save(carModels);
    }

    @Override
    public List<Company> findAll() {
        return carModelsRepository.findAll();
    }
}
