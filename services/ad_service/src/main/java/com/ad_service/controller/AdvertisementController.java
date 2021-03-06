package com.ad_service.controller;

import com.ad_service.dto.CarDTO;
import com.ad_service.dto.PricingDTO;
import com.ad_service.dto.RentRequestDTO;
import com.ad_service.model.*;
import com.ad_service.model.enums.RequestStatus;
import com.ad_service.service.interfaces.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
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




    @PostMapping(value="/pricing")
    public ResponseEntity<?> addPricing(@RequestBody PricingDTO dto, Principal p){
        try{

            String user = (p.getName());
            Pricing c = new Pricing();
            c.setOwner(user);

            c.setCollisionDamage(dto.getCollisionDamage());
            c.setName(dto.getName());
            c.setDiscountDays(dto.getDiscountDays());
            c.setDiscountPercent(dto.getDiscountPercent());
            c.setDistanceLimit(dto.getDistanceLimit());
            c.setOverusePrice(dto.getOverusePrice());
            c.setRegularPrice(dto.getRegularPrice());



            pricingService.save(c);
            return ResponseEntity.ok().build();
        }catch (Exception e){
        }
        return ResponseEntity.status(400).build();
    }

    @GetMapping(value="/pricing")
    public List<PricingDTO> getPricing(Principal p){
        List<Pricing> c = pricingService.findAll();

        List<PricingDTO> dto = new ArrayList<>();
        String user = p.getName();

        for(Pricing a : c) {
            if (a.getOwner().equals(user)) {


                PricingDTO d = new PricingDTO();
                d.setName(a.getName());
                d.setCollisionDamage(a.getCollisionDamage());
                d.setDiscountDays(a.getDiscountDays());
                d.setDiscountPercent(a.getDiscountPercent());
                d.setDistanceLimit(a.getDistanceLimit());
                d.setOverusePrice(a.getOverusePrice());
                d.setRegularPrice(a.getRegularPrice());
                d.setOwner(this.userFeignClient.findByEmail(user));
                dto.add(d);
            }
        }

        return dto;
    }


    @PostMapping(value="/addCar")
    public ResponseEntity<?> addCar(@RequestBody CarDTO dto){
        try{
            Cars c = new Cars();
            CarModels cm = carModelsService.findByName(dto.getCarModel());
            c.setModel(cm);
            Pricing p = pricingService.findByName(dto.getPricing());
            c.setPricing(p);
            c.setOwner(p.getOwner());
            FuelType ft = fuelTypeService.findByName(dto.getFuelType());
            c.setFuelType(ft);
            c.setMilage(dto.getMilage());
            c.setName(dto.getName());
            c.setTown(dto.getTown());

            c.setAndroidGps(null);

            carsService.save(c);
            return ResponseEntity.ok().build();







        }catch (Exception e){
        }
        return ResponseEntity.status(400).build();
    }

    @GetMapping(value="/getCars")
    public List<CarDTO> getCars(Principal p){
        List<Cars> c = carsService.findAll();

        List<CarDTO> dto = new ArrayList<>();

        String user = (p.getName());

        for(Cars a : c) {
            if (a.getOwner().equals(user)) {
                CarDTO d = new CarDTO();
                d.setName(a.getName());
                d.setCarModel(a.getModel().getName());
                d.setFuelType(a.getFuelType().getName());
                d.setMilage(a.getMilage());
                d.setPricing(a.getPricing().getName());

                dto.add(d);
            }
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
            e.printStackTrace();
        }
        return ResponseEntity.status(400).build();
    }



}