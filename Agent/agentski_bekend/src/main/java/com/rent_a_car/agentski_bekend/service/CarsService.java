package com.rent_a_car.agentski_bekend.service;

import com.rent_a_car.agentski_bekend.dto.RentRequestDTO;
import com.rent_a_car.agentski_bekend.model.Cars;
import com.rent_a_car.agentski_bekend.model.RentRequest;
import com.rent_a_car.agentski_bekend.model.enums.RequestStatus;
import com.rent_a_car.agentski_bekend.repository.CarsRepository;
import com.rent_a_car.agentski_bekend.repository.RentRequestRepository;
import com.rent_a_car.agentski_bekend.service.interfaces.CarsServiceInterface;
import com.rent_a_car.agentski_bekend.service.interfaces.RentRequestServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
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


