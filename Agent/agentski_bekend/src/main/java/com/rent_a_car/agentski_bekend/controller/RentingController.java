package com.rent_a_car.agentski_bekend.controller;

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

    @GetMapping(value = "test")
    public String test () {
        return "Renting service test";
    }

    @GetMapping(value = "getCars")
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


    @PostMapping(value="approveRentRequest")
    public ResponseEntity<?> approveRentRequest(@RequestBody Integer id){
        RentRequest u = rentRequestService.findById(id);
        u.setStatus(RequestStatus.RESERVED); //odobren j e
        rentRequestService.save(u);   // nzm treba li
        return ResponseEntity.ok().build();
    }
}
