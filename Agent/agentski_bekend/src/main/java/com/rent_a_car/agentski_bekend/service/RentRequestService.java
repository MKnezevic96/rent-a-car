package com.rent_a_car.agentski_bekend.service;
import com.rent_a_car.agentski_bekend.model.RentRequest;
import com.rent_a_car.agentski_bekend.model.UserRequest;
import com.rent_a_car.agentski_bekend.model.enums.RequestStatus;
import com.rent_a_car.agentski_bekend.repository.RentRequestRepository;
import com.rent_a_car.agentski_bekend.repository.UserRequestRepository;
import com.rent_a_car.agentski_bekend.service.interfaces.RentRequestServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class RentRequestService implements RentRequestServiceInterface {
    @Autowired
    private RentRequestRepository userRequestRepository;


//    @Autowired
//    private CarsService carsService;

//    @Autowired
//    private RentRequest rentRequest;

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