package com.rent_a_car.agentski_bekend.service;

import com.rent_a_car.agentski_bekend.dto.RentRequestDTO;
import com.rent_a_car.agentski_bekend.model.Cars;
import com.rent_a_car.agentski_bekend.model.RentRequest;
import com.rent_a_car.agentski_bekend.model.enums.RequestStatus;
import com.rent_a_car.agentski_bekend.repository.CarsRepository;
import com.rent_a_car.agentski_bekend.repository.RentRequestRepository;
import com.rent_a_car.agentski_bekend.service.interfaces.CarsServiceInterface;
import org.hibernate.Hibernate;
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
    private RentRequestRepository rentRequestRepository;

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



    public void autoReject(RentRequestDTO rentRequestDto) {


            List<RentRequest> rentRequests = this.rentRequestRepository.findAll();
            for (RentRequest request : rentRequests) {
                if (request.getStatus().equals(RequestStatus.PENDING) && !checkDates(rentRequestDto, request)  // ako je na cekanju i ako su zauzeti datumi
                ) {

                        for (Cars a : carsRepository.findAll()) {
                            if (a.getName().equals(rentRequestDto.getCarName())) {
                                request.setStatus(RequestStatus.CANCELED);
                                this.rentRequestRepository.save(request);
                            }
                        }
                    }
                }
            }


    public boolean checkDates(RentRequestDTO rentRequestDto, RentRequest request) {
        return (rentRequestDto.getEndDate().before(request.getStartDate()) || rentRequestDto.getStartDate().after(request.getEndDate()));
    }

}


