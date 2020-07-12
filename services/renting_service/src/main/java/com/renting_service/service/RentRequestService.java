package com.renting_service.service;

import com.renting_service.model.RentRequest;
import com.renting_service.model.enums.RequestStatus;
import com.renting_service.repository.RentRequestRepository;
import com.renting_service.service.interfaces.RentRequestServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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

    @Override
    public void canclePendingReservations(Date startDate, Date endDate, Integer id) {

        List<RentRequest> rrList = findAll();

        for (RentRequest rr : rrList) {
            if (rr.getCarId().getId().equals(id)) {
                if (rr.getStatus().equals(RequestStatus.PENDING)) {
                    if (startDate.before(rr.getStartDate()) && endDate.after(rr.getStartDate())) {
                        rr.setStatus(RequestStatus.CANCELED);
                    }
                    if (startDate.after(rr.getStartDate()) && endDate.before(rr.getEndDate())) {
                        rr.setStatus(RequestStatus.CANCELED);
                    }
                    if (startDate.before(rr.getEndDate()) && endDate.after(rr.getEndDate())) {
                        rr.setStatus(RequestStatus.CANCELED);
                    }
                    save(rr);
                }
            }
        }
    }

}
