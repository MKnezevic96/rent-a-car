package com.admin_service.service;
import com.admin_service.model.RentRequest;
import com.admin_service.repository.RentRequestRepository;
import com.admin_service.service.interfaces.RentRequestServiceInterface;
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
    public RentRequest save(RentRequest user) {
        return userRequestRepository.save(user);
    }
    @Override
    public List<RentRequest> findAll() {
        List<RentRequest> result = userRequestRepository.findAll();
        return result;
    }
}