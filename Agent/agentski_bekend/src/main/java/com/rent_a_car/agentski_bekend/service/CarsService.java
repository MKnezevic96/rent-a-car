package com.rent_a_car.agentski_bekend.service;

import com.rent_a_car.agentski_bekend.dto.RentRequestDTO;
import com.rent_a_car.agentski_bekend.dto.RentingReportDTO;
import com.rent_a_car.agentski_bekend.model.CarReview;
import com.rent_a_car.agentski_bekend.model.Cars;
import com.rent_a_car.agentski_bekend.model.RentRequest;
import com.rent_a_car.agentski_bekend.model.RentingReport;
import com.rent_a_car.agentski_bekend.model.enums.RequestStatus;
import com.rent_a_car.agentski_bekend.repository.CarsRepository;
import com.rent_a_car.agentski_bekend.repository.RentRequestRepository;
import com.rent_a_car.agentski_bekend.service.interfaces.CarsServiceInterface;
import org.hibernate.Hibernate;
import com.rent_a_car.agentski_bekend.service.interfaces.RentRequestServiceInterface;
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
    private RentingReportService rentingReportService;

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

    @Override
    public Cars getCar (Integer id) {
        return carsRepository.getOne(id);
    }


    @Override
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

    @Override
    public boolean checkDates(RentRequest rentRequestDto, RentRequest request) {
        return (rentRequestDto.getEndDate().before(request.getStartDate()) || rentRequestDto.getStartDate().after(request.getEndDate()));
    }

    @Override
    public String calculateAverageRating(Integer carId){
        float avrg = 0;
        float i = 0;
        float sum = 0;

        Cars car = carsRepository.getOne(carId);
        List<CarReview> reviews = car.getReviews();

        for(CarReview review : reviews) {
            if(review.getRating() != null) {
                i++;
                sum = sum + review.getRating();
            }
        }

        avrg = sum / i;
        return String.valueOf(avrg);
    }


    @Override
    public double calculateMileageNumber(Integer carId){

        double sum = 0;

        List<RentingReport> reports = rentingReportService.findAll();

        for(RentingReport rr : reports) {
            if(rr.getRentingInstance().getCarId().getId().equals(carId)) {
                sum = sum + rr.getAddedMileage();
            }
        }

        return sum;
    }


    @Override
    public String calculateCommentsNumber(Integer carId){

        int i = 0;

        Cars car = carsRepository.getOne(carId);
        List<CarReview> reviews = car.getReviews();

        for(CarReview review : reviews) {
            if(review.isApproved()) {
                i++;
            }
        }

        return String.valueOf(i);
    }
}


