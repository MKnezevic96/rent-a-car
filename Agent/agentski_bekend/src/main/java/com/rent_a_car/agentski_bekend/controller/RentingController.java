package com.rent_a_car.agentski_bekend.controller;

import com.rent_a_car.agentski_bekend.AgentskiBekendApplication;
import com.rent_a_car.agentski_bekend.dto.*;
import com.rent_a_car.agentski_bekend.model.*;
import com.rent_a_car.agentski_bekend.model.enums.RequestStatus;
import com.rent_a_car.agentski_bekend.service.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.math.BigInteger;
import org.springframework.web.bind.annotation.*;



import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping(value = "api/renting/")
public class RentingController {

    @Autowired
    private CarsService carsService;

    @Autowired
    private RentRequestService rentRequestService;

    @Autowired
    private RentingReportService rentingReportService;

    @Autowired
    private CarReviewService carReviewService;

    @Autowired
    private UserService userService;


    private static final Logger LOGGER = LogManager.getLogger(RentingController.class.getName());

    @GetMapping(value = "test")
    public String test () {
        return "Renting service test";
    }

    @GetMapping(value = "cars")
    public ResponseEntity<List<CarsListingDTO>> getAllCars () {
        ArrayList<CarsListingDTO> retVal = new ArrayList<CarsListingDTO>();
        for (Cars c : carsService.findAll()) {
            if (c.getId() != null) {
                retVal.add(new CarsListingDTO(c));
            }
        }
        LOGGER.info("Action get all cars successful");
        return new ResponseEntity<List<CarsListingDTO>>(retVal, HttpStatus.OK);
    }


    @GetMapping(value = "get/{t}")
    public ResponseEntity<List<CarsListingDTO>> filterCarsByTown (@PathVariable("t") String t) {
        ArrayList<CarsListingDTO> retVal = new ArrayList<CarsListingDTO>();

        for (Cars c : carsService.filterByCity((ArrayList<Cars>) carsService.findAll(), t.replaceAll("_", " "))) {
            retVal.add(new CarsListingDTO(c));
        }
        return new ResponseEntity<List<CarsListingDTO>>(retVal, HttpStatus.OK);
    }

    @GetMapping (value = "cars/{id}")
    public ResponseEntity<CarsDetailsDTO> getOneCar (@PathVariable("id") Integer id) {
        try {
            CarsDetailsDTO retVal = new CarsDetailsDTO(carsService.getCar(id));

            LOGGER.info("Action get car id:{} successful", id);
            return new ResponseEntity<CarsDetailsDTO>(retVal, HttpStatus.OK);
        } catch(Exception e) {
            LOGGER.error("Action get car id:{} failed. Cause:{}", id, e.getMessage());
        }

        return ResponseEntity.status(400).build();
    }

    @GetMapping(value = "requests")
    public ResponseEntity<List<RentRequestDTO>> getAllRentRequests (@RequestParam(value = "status", required = false) String status) {
        ArrayList<RentRequestDTO> retVal = new ArrayList<RentRequestDTO>();

        for (RentRequest rr : rentRequestService.findAll()) {
            if(status == null){
                retVal.add(new RentRequestDTO(rr));
            } else if(status.equals("paid")) {
                if(rr.getStatus() == RequestStatus.PAID)
                    retVal.add(new RentRequestDTO(rr));
            }
        }
        LOGGER.info("Action get all rent requests successful");
        return new ResponseEntity<List<RentRequestDTO>>(retVal, HttpStatus.OK);
    }


    @GetMapping(value = "requests/group/{id}")
    public ResponseEntity<List<RentRequestDTO>> getGroupRequests (@PathVariable("id") Integer groupId) {
        ArrayList<RentRequestDTO> retVal = new ArrayList<RentRequestDTO>();
        for (RentRequest rr : rentRequestService.findAll()) {
            if(rr.getRequestGroupId().equals(groupId))
               retVal.add(new RentRequestDTO(rr));
        }
        LOGGER.info("Action get group rent requests successful");
        return new ResponseEntity<List<RentRequestDTO>>(retVal, HttpStatus.OK);
    }

    @PostMapping(value ="/report", consumes = MediaType.APPLICATION_JSON)
    public ResponseEntity<?> addRentingReport(@RequestBody RentingReportDTO dto) {

        try {
            RentingReport report = new RentingReport();
            report.setAddedMileage(dto.getAddedMileage());
            report.setDeleted(false);
            report.setReport(dto.getReport());

            RentRequest req = rentRequestService.findById(dto.getRentingInstanceId());

            report.setRentingInstance(rentRequestService.findById(dto.getRentingInstanceId()));
            rentingReportService.save(report);

            req.setStatus(RequestStatus.RETURNED);
            req.setRentingReport(report);
            rentRequestService.save(req);
            LOGGER.info("Action add renting report for request id:{} successful", dto.getRentingInstanceId());
            return ResponseEntity.status(200).build();
        } catch(Exception e) {
            LOGGER.error("Action add renting report for request id:{} failed. Cause: {}", dto.getRentingInstanceId(), e.getMessage());
        }
        return ResponseEntity.status(400).build();

    }

    @PostMapping(value="/review", consumes = MediaType.APPLICATION_JSON)
    public ResponseEntity<?> addReview(@RequestBody CarReviewDTO dto){

        try{
            CarReview review = new CarReview();
            review.setDeleted(false);
            review.setRating(dto.getRating());
            review.setReview(dto.getReview());
            Cars car = carsService.getCar(dto.getCarId());
            review.setCar(car);
            //review.setReviewer(userService.findUserById(dto.getReviewerId()));
            review.setReviewer(userService.findUserById(1));
            //TODO: promeniti reviewer id kad dodju tokeni
            carReviewService.save(review);
            LOGGER.info("Action add car review for car id:{} successful", dto.getCarId());
            return ResponseEntity.ok().build();
        }catch (Exception e){
            LOGGER.error("Action add car review for car id={} failed. Cause: {}", dto.getCarId(), e.getMessage());

        }
        return ResponseEntity.status(400).build();
    }

}
