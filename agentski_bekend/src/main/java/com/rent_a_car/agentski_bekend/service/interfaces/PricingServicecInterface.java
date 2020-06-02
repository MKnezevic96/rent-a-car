package com.rent_a_car.agentski_bekend.service.interfaces;

import com.rent_a_car.agentski_bekend.model.CarClass;
import com.rent_a_car.agentski_bekend.model.Pricing;

import java.util.List;

public interface PricingServicecInterface {
    Pricing findByName(String name);
    Pricing save(Pricing pricing);
    List<Pricing> findAll();
}
