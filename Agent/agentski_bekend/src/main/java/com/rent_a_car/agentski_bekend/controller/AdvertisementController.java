package com.rent_a_car.agentski_bekend.controller;
import com.rent_a_car.agentski_bekend.dto.CarDTO;
import com.rent_a_car.agentski_bekend.dto.RentRequestDTO;
import com.rent_a_car.agentski_bekend.model.*;
import com.rent_a_car.agentski_bekend.model.enums.RequestStatus;
import com.rent_a_car.agentski_bekend.service.interfaces.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AdvertisementController {


    @Autowired
    private PricingServicecInterface pricingService;

    @Autowired
    private CarClassServiceInterface carClassService;

    @Autowired
    private CarModelsServiceInterface carModelsService;

    @Autowired
    private FuelTypeServiceInterface fuelTypeService;

    @Autowired
    private CarsServiceInterface carsService;

    @Autowired
    private RentRequestServiceInterface rentRequestService;



    @PostMapping(value="/addPricing")
    public ResponseEntity<?> addPricing(@RequestBody Pricing pricing){
        try{
            pricingService.save(pricing);
            return ResponseEntity.ok().build();
        }catch (Exception e){
        }
        return ResponseEntity.status(400).build();
    }

    @PostMapping(value="/addCar")
    public ResponseEntity<?> addCar(@RequestBody CarDTO dto){
        try{
            Cars c = new Cars();
            CarModels cm = carModelsService.findByName(dto.getCarModel());
            c.setModel(cm);
            Pricing p = pricingService.findByName(dto.getName());
            c.setPricing(p);
            FuelType ft = fuelTypeService.findByName(dto.getFuelType());
            c.setFuelType(ft);
            c.setMilage(dto.getMilage());
            c.setName(dto.getName());

            c.setAndroidGps(null);
            c.setOwner(null);

            carsService.save(c);
            return ResponseEntity.ok().build();
        }catch (Exception e){
        }
        return ResponseEntity.status(400).build();
    }

    @GetMapping(value="/getCars")
    public List<CarDTO> getCars(){
        List<Cars> c = carsService.findAll();

        List<CarDTO> dto = new ArrayList<>();

        for(Cars a : c){
            CarDTO d = new CarDTO();
            d.setName(a.getName());
            d.setCarModel(a.getModel().getName());
            d.setFuelType(a.getFuelType().getName());
            d.setMilage(a.getMilage());
            d.setPricing(a.getPricing().getName());

            dto.add(d);
        }

        return dto;
    }

    @PostMapping(value="/rentCar")
    public ResponseEntity<?> rentCar(@RequestBody RentRequestDTO dto){

        try{
            RentRequest rr = new RentRequest();
            Cars c = carsService.findByName(dto.getCarName());
            rr.setCarId(c);
            rr.setStartDate(dto.getStartDate());
            rr.setEndDate(dto.getEndDate());
            rr.setStatus(RequestStatus.PENDING);
            rr.setDeleted(false);
            rentRequestService.save(rr);
            return ResponseEntity.ok().build();
        }catch (Exception e){
        }
        return ResponseEntity.status(400).build();
    }
}