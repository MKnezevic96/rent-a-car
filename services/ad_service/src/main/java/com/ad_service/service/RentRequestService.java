package com.ad_service.service;

import com.ad_service.model.RentRequest;
import com.ad_service.repository.RentRequestRepository;
import com.ad_service.service.interfaces.RentRequestServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class RentRequestService implements RentRequestServiceInterface {

    @Autowired
    private RentRequestRepository userRequestRepository;




    @Override
    public RentRequest findById(Integer id) {
        return userRequestRepository.findById(id).orElse(null);
    }

    @Override
    public RentRequest save(RentRequest request) {
        return userRequestRepository.save(request);
    }

    @Override
    public List<RentRequest> findAll() {
        List<RentRequest> result = userRequestRepository.findAll();
        return result;
    }


}