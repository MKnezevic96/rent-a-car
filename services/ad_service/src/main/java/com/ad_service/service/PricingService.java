package com.ad_service.service;


import com.ad_service.model.Pricing;
import com.ad_service.repository.PricingRepository;
import com.ad_service.service.interfaces.PricingServicecInterface;
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
