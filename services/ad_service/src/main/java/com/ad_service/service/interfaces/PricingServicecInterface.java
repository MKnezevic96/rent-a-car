package com.ad_service.service.interfaces;



import com.ad_service.model.Pricing;

import java.util.List;

public interface PricingServicecInterface {
    Pricing findByName(String name);
    Pricing save(Pricing pricing);
    List<Pricing> findAll();
}