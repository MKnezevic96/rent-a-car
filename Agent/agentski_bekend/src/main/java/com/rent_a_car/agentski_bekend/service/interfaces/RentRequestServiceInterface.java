package com.rent_a_car.agentski_bekend.service.interfaces;
import com.rent_a_car.agentski_bekend.model.RentRequest;

import java.util.Date;
import java.util.List;
public interface RentRequestServiceInterface {
     RentRequest findById(Integer id);
     RentRequest save(RentRequest request);
     List<RentRequest> findAll();
     void canclePendingReservations(Date startDate, Date endDate, Integer id);
}
