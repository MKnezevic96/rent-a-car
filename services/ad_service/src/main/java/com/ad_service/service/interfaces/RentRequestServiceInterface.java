package com.ad_service.service.interfaces;
import com.ad_service.model.RentRequest;

import java.util.List;
public interface RentRequestServiceInterface {
    RentRequest findById(Integer id);
    RentRequest save(RentRequest request);
    List<RentRequest> findAll();
}

