package com.admin_service.service;

import com.admin_service.dto.RentRequestDTO;
import com.admin_service.model.Cars;
import com.admin_service.model.RentRequest;
import com.admin_service.model.enums.RequestStatus;
import com.admin_service.repository.CarsRepository;
import com.admin_service.repository.RentRequestRepository;
import com.admin_service.service.interfaces.CarsServiceInterface;
import org.hibernate.Hibernate;
import com.admin_service.service.interfaces.RentRequestServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CarsService implements CarsServiceInterface {
    @Autowired
    private CarsRepository carsRepository;

    //    private RentRequestRepository rentRequestRepository;
    @Autowired
    private RentRequestServiceInterface rentRequestService;

    //  @Override
    //   public Cars findByName(String name) {
    //      return carModelsRepository.findByName(name);
    //  }

    @Override
    public Cars findByName(String name) {
        return carsRepository.findByName(name);
    }

    @Override
    public Cars save(Cars carModels) {
        return carsRepository.save(carModels);
    }

    @Override
    public List<Cars> findAll() {
        return carsRepository.findAll();
    }

    public List<Cars> filterByCity (ArrayList<Cars> cars, String town) {
        ArrayList<Cars> retVal = new ArrayList<Cars> ();
        for (Cars car : cars) {
            if (car.getTown().equals(town)) {
                retVal.add(car);
            }
        }
        return retVal;
    }

    public Cars getCar (Integer id) {
        return carsRepository.getOne(id);
    }



    public void autoReject(RentRequest rentRequestDto) {


        List<RentRequest> rentRequests = rentRequestService.findAll();
        for (RentRequest request : rentRequests) {
            if (request.getStatus().equals(RequestStatus.PENDING) && !checkDates(rentRequestDto, request)  // ako je na cekanju i ako su zauzeti datumi
            ) {

                for (Cars a : carsRepository.findAll()) {
                    if (a.getId().equals(rentRequestDto.getCarId())) {
                        request.setStatus(RequestStatus.CANCELED);
                        rentRequestService.save(request);
                    }
                }
            }
        }
    }


    public boolean checkDates(RentRequest rentRequestDto, RentRequest request) {
        return (rentRequestDto.getEndDate().before(request.getStartDate()) || rentRequestDto.getStartDate().after(request.getEndDate()));
    }
}
