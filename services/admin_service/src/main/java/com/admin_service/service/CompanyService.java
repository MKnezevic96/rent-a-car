package com.admin_service.service;

import com.admin_service.model.Company;
import com.admin_service.repository.CompanyRepository;
import com.admin_service.service.interfaces.CompanyServiceInterface;
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
