package com.rent_a_car.agentski_bekend.controller;

import com.rent_a_car.agentski_bekend.dto.*;
import com.rent_a_car.agentski_bekend.model.*;
import com.rent_a_car.agentski_bekend.model.enums.RequestStatus;
import com.rent_a_car.agentski_bekend.service.*;

import com.rent_a_car.agentski_bekend.service.interfaces.RentRequestServiceInterface;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.rent_a_car.agentski_bekend.dto.CarsDetailsDTO;
import com.rent_a_car.agentski_bekend.dto.CarsListingDTO;
import com.rent_a_car.agentski_bekend.dto.RentRequestDTO;
import com.rent_a_car.agentski_bekend.model.Cars;
import com.rent_a_car.agentski_bekend.model.RentRequest;
import com.rent_a_car.agentski_bekend.model.User;
import com.rent_a_car.agentski_bekend.model.enums.RequestStatus;
import com.rent_a_car.agentski_bekend.repository.CarsRepository;
import com.rent_a_car.agentski_bekend.service.CarsService;
import com.rent_a_car.agentski_bekend.service.interfaces.RentRequestServiceInterface;
import com.rent_a_car.agentski_bekend.service.interfaces.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.security.Principal;
import java.math.BigInteger;
import org.springframework.web.bind.annotation.*;



import javax.ws.rs.core.MediaType;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "api/renting/")
public class RentingController {

    @Autowired
    private CarsService carsService;

    @Autowired
    private UserServiceInterface userService;

    @Autowired
    private RentRequestServiceInterface rentRequestService;

    @Autowired
    private RentingReportService rentingReportService;

    @Autowired
    private CarReviewService carReviewService;


    private static final Logger LOGGER = LogManager.getLogger(RentingController.class.getName());

    @GetMapping(value = "test")
    public String test () {
        return "Renting service test";
    }

    //@GetMapping(value = "getCars")
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

    @GetMapping(value = "payRequests")
    public List<RentRequestDTO> getRequestsForPayment (Principal p) {
        List<RentRequestDTO> dto = new ArrayList<>();
        List<RentRequest> rrl = rentRequestService.findAll();
        User user = userService.findByEmail(p.getName());
        for(RentRequest rr : rrl){
            if (rr.getStatus().equals(RequestStatus.RESERVED)) {
                if (rr.getOwningUser().equals(user)) {
                    RentRequestDTO rrdto = new RentRequestDTO();
                    rrdto.setCarName(rr.getCarId().getName());
                    rrdto.setStartDate(rr.getStartDate());
                    rrdto.setEndDate(rr.getEndDate());
                    rrdto.setStatus("RESERVED");
                    rrdto.setId(rr.getId());
                    dto.add(rrdto);
                }
            }
        }


        return dto;
    }

    @GetMapping(value = "rentRequests")
    public List<RentRequestDTO> getRentRequests (Principal p) {
        List<RentRequest> retVal = rentRequestService.findAll();
        List<RentRequestDTO> dto = new ArrayList<>() ;
        User user = userService.findByEmail(p.getName());
        for (RentRequest c : retVal) {
            if(c.getCarId().getOwner().equals(user)) {
                RentRequestDTO dto1 = new RentRequestDTO();
                dto1.setId(c.getId());
                dto1.setStartDate(c.getStartDate());
                dto1.setEndDate(c.getEndDate());
                if (c.getStatus().equals(RequestStatus.PENDING)) {
                    dto1.setStatus("PENDING");
                } else if (c.getStatus().equals(RequestStatus.CANCELED)) {
                    dto1.setStatus("CANCELED");
                } else if (c.getStatus().equals(RequestStatus.PAID)) {
                    dto1.setStatus("PAID");
                } else if (c.getStatus().equals(RequestStatus.RESERVED)) {
                    dto1.setStatus("RESERVED");
                } else if (c.getStatus().equals(RequestStatus.RETURNED)) {
                    dto1.setStatus("RETURNED");
                }
                dto.add(dto1);
            }
        }
        return dto;
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

//        try{
//            CarReview review = new CarReview();
//            review.setDeleted(false);
//            review.setRating(dto.getRating());
//            review.setReview(dto.getReview());
//            Cars car = carsService.getCar(dto.getCarId());
//            review.setCar(car);
//            //review.setReviewer(userService.findUserById(dto.getReviewerId()));
//            review.setReviewer(userService.findUserById(1));
//            //TODO: promeniti reviewer id kad dodju tokeni
//            carReviewService.save(review);
//            LOGGER.info("Action add car review for car id:{} successful", dto.getCarId());
//            return ResponseEntity.ok().build();
//        }catch (Exception e){
//            LOGGER.error("Action add car review for car id={} failed. Cause: {}", dto.getCarId(), e.getMessage());
//
//        }
        return ResponseEntity.status(400).build();
    }
    @GetMapping(value = "rentRequests")
    public List<RentRequestDTO> getRentRequests (Principal p) {
       List<RentRequest> retVal = rentRequestService.findAll();
        List<RentRequestDTO> dto = new ArrayList<>() ;
        User user = userService.findByEmail(p.getName());

        for (RentRequest c : retVal) {
            if(c.getCarId().getOwner().equals(user)) {


                if (c.getStatus().equals(RequestStatus.PENDING)) {
                    RentRequestDTO dto1 = new RentRequestDTO();
                    dto1.setId(c.getId());
                    dto1.setStartDate(c.getStartDate());
                    dto1.setEndDate(c.getEndDate());
                    dto1.setCarName(c.getCarId().getName());
                    dto1.setStatus("PENDING");
                    dto.add(dto1);
//                } else if (c.getStatus().equals(RequestStatus.CANCELED)) {
//                    dto1.setStatus("CANCELED");
//                } else if (c.getStatus().equals(RequestStatus.PAID)) {
//                    dto1.setStatus("PAID");
//                } else if (c.getStatus().equals(RequestStatus.RESERVED)) {
//                    dto1.setStatus("RESERVED");
//                } else if (c.getStatus().equals(RequestStatus.RETURNED)) {
//                    dto1.setStatus("RETURNED");
                }

                //dto.add(dto1);
            }
        }

        return dto;
    }

    @PostMapping(value="/rentCar")
    public ResponseEntity<?> rentCar(@RequestBody RentRequestDTO dto, Principal p){
        User user = userService.findByEmail(p.getName());

        try{
            RentRequest rr = new RentRequest();
            Cars c = carsService.findByName(dto.getCarName());
            rr.setCarId(c);
            rr.setStartDate(dto.getStartDate());
            rr.setEndDate(dto.getEndDate());
            rr.setStatus(RequestStatus.PENDING);
            rr.setDeleted(false);
            rr.setOwningUser(user);
            rentRequestService.save(rr);
            return ResponseEntity.ok().build();
        }catch (Exception e){
        }
        return ResponseEntity.status(400).build();
    }

    @PostMapping(value="approveRentRequest")
    public ResponseEntity<?> approveRentRequest(@RequestBody Integer id){
        RentRequest u = rentRequestService.findById(id);
        u.setStatus(RequestStatus.RESERVED); //odobren j e
        rentRequestService.save(u);   // nzm treba li
        // todo odbiti koji se preklapaju
      //  carsService.autoReject(u);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value="rejectRentRequest")
    public ResponseEntity<?> rejectRentRequest(@RequestBody Integer id){
        RentRequest u = rentRequestService.findById(id);
        u.setStatus(RequestStatus.CANCELED); //otkazan j e
        rentRequestService.save(u);   // nzm treba li
        return ResponseEntity.ok().build();
    }
}
