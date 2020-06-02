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
