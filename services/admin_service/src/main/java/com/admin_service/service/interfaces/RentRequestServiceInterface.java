package com.admin_service.service.interfaces;

import com.admin_service.model.RentRequest;

import java.util.List;

public interface RentRequestServiceInterface {
    public RentRequest findById(Integer id);
    public RentRequest save(RentRequest request);
    public List<RentRequest> findAll();
}