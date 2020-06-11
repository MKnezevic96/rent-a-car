package com.rent_a_car.agentski_bekend.controller;

import com.rent_a_car.agentski_bekend.dto.CarsDetailsDTO;
import com.rent_a_car.agentski_bekend.dto.CarsListingDTO;
import com.rent_a_car.agentski_bekend.dto.RentRequestDTO;
import com.rent_a_car.agentski_bekend.dto.RentingReportDTO;
import com.rent_a_car.agentski_bekend.model.Cars;
import com.rent_a_car.agentski_bekend.model.RentRequest;
import com.rent_a_car.agentski_bekend.model.RentingReport;
import com.rent_a_car.agentski_bekend.model.User;
import com.rent_a_car.agentski_bekend.model.enums.RequestStatus;
import com.rent_a_car.agentski_bekend.repository.CarsRepository;
import com.rent_a_car.agentski_bekend.security.auth.JwtAuthenticationRequest;
import com.rent_a_car.agentski_bekend.service.CarsService;
import com.rent_a_car.agentski_bekend.service.RentRequestService;
import com.rent_a_car.agentski_bekend.service.RentingReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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

    @GetMapping(value = "test")
    public String test () {
        return "Renting service test";
    }

    @GetMapping(value = "getAllCars")
    public ResponseEntity<List<CarsListingDTO>> getAllCars () {
        ArrayList<CarsListingDTO> retVal = new ArrayList<CarsListingDTO>();
        for (Cars c : carsService.findAll()) {
            if (c.getId() != null) {
                retVal.add(new CarsListingDTO(c));
            }
        }
        return new ResponseEntity<List<CarsListingDTO>>(retVal, HttpStatus.OK);
    }


    @GetMapping(value = "get/{t}")
    public ResponseEntity<List<CarsListingDTO>> filterCarsByTown (@PathVariable("t") String t) {
        ArrayList<CarsListingDTO> retVal = new ArrayList<CarsListingDTO>();
        System.out.println("   >>> " + t);
        for (Cars c : carsService.filterByCity((ArrayList<Cars>) carsService.findAll(), t.replaceAll("_", " "))) {
            retVal.add(new CarsListingDTO(c));
        }
        return new ResponseEntity<List<CarsListingDTO>>(retVal, HttpStatus.OK);
    }

    @GetMapping (value = "getOne/{id}")
    public ResponseEntity<CarsDetailsDTO> getOneCar (@PathVariable("id") Integer id) {
        CarsDetailsDTO retVal = new CarsDetailsDTO(carsService.getCar(id));

        return new ResponseEntity<CarsDetailsDTO>(retVal, HttpStatus.OK);

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
        return new ResponseEntity<List<RentRequestDTO>>(retVal, HttpStatus.OK);
    }


    @GetMapping(value = "requests/group/{id}")
    public ResponseEntity<List<RentRequestDTO>> getGroupRequests (@PathVariable("id") Integer groupId) {
        ArrayList<RentRequestDTO> retVal = new ArrayList<RentRequestDTO>();
        for (RentRequest rr : rentRequestService.findAll()) {
            if(rr.getRequestGroupId().equals(groupId))
               retVal.add(new RentRequestDTO(rr));
        }
        return new ResponseEntity<List<RentRequestDTO>>(retVal, HttpStatus.OK);
    }

    @PostMapping(value ="/report", consumes = MediaType.APPLICATION_JSON)
    public ResponseEntity<?> addRentingReport(@RequestBody RentingReportDTO dto) {

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

        return ResponseEntity.status(200).build();

    }

}
