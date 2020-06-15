package com.rent_a_car.agentski_bekend.service.interfaces;
import com.rent_a_car.agentski_bekend.model.RentRequest;
import java.util.List;
public interface RentRequestServiceInterface {
    public RentRequest findById(Integer id);
    public RentRequest save(RentRequest request);
    public List<RentRequest> findAll();
}
