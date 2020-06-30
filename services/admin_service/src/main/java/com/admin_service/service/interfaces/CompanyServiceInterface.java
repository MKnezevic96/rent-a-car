package com.admin_service.service.interfaces;

import com.admin_service.model.Company;

import java.util.List;

public interface CompanyServiceInterface {

    Company findByName(String name);
    Company save(Company company);
    List<Company> findAll();
}
