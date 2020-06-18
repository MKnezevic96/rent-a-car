package com.admin_service.service;

import com.admin_service.model.Pricing;
import com.admin_service.repository.PricingRepository;
import com.admin_service.service.interfaces.PricingServicecInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PricingService implements PricingServicecInterface {

    @Autowired
    private PricingRepository carModelsRepository;

    @Override
    public Pricing findByName(String name) {
        return carModelsRepository.findByName(name);
    }

    @Override
    public Pricing save(Pricing carModels) {
        return carModelsRepository.save(carModels);
    }

    @Override
    public List<Pricing> findAll() {
        return carModelsRepository.findAll();
    }
}
