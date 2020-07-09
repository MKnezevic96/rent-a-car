package com.renting_service.service.interfaces;

import com.renting_service.model.RentRequest;

import java.util.Date;
import java.util.List;

public interface RentRequestServiceInterface {
    RentRequest findById(Integer id);
    RentRequest save(RentRequest request);
    List<RentRequest> findAll();
    void canclePendingReservations(Date startDate, Date endDate, Integer id);
}
