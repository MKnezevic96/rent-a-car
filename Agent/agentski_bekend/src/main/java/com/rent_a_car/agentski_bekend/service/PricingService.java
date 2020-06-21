package com.rent_a_car.agentski_bekend.service;

import com.rent_a_car.agentski_bekend.model.CarModels;
import com.rent_a_car.agentski_bekend.model.Pricing;
import com.rent_a_car.agentski_bekend.repository.CarModelsRepository;
import com.rent_a_car.agentski_bekend.repository.PricingRepository;
import com.rent_a_car.agentski_bekend.service.interfaces.PricingServicecInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PricingService implements PricingServicecInterface {

    @Autowired
    private PricingRepository pricingRepository;

    @Override
    public Pricing findByName(String name) {
        return pricingRepository.findByName(name);
    }

    @Override
    public Pricing save(Pricing pricing) {
        return pricingRepository.save(pricing);
    }

    @Override
    public List<Pricing> findAll() {
        return pricingRepository.findAll();
    }
}
