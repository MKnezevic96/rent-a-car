package com.rent_a_car.agentski_bekend.controller;
import com.rent_a_car.agentski_bekend.dto.CarDTO;
import com.rent_a_car.agentski_bekend.dto.CarsDetailsDTO;
import com.rent_a_car.agentski_bekend.dto.PricingDTO;
import com.rent_a_car.agentski_bekend.dto.RentRequestDTO;
import com.rent_a_car.agentski_bekend.model.*;
import com.rent_a_car.agentski_bekend.model.enums.RequestStatus;
import com.rent_a_car.agentski_bekend.service.CarsService;
import com.rent_a_car.agentski_bekend.service.interfaces.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
    private CarsService carsService;

    @Autowired
    private RentRequestServiceInterface rentRequestService;

    @Autowired
    private UserServiceInterface userService;


    private static final Logger LOGGER = LogManager.getLogger(RentingController.class.getName());


    @PreAuthorize("hasAuthority('ad_menagement_write')")
    @PostMapping(value="/pricing")
    public ResponseEntity<?> addPricing(@RequestBody PricingDTO dto, Principal p){

        try{

            User user = userService.findByEmail(p.getName());
            Pricing c = new Pricing();
            User cm = userService.findByEmail(dto.getOwner());
            c.setOwner(user);

            c.setCollisionDamage(dto.getCollisionDamage());
            c.setName(dto.getName());
            c.setDiscountDays(dto.getDiscountDays());
            c.setDiscountPercent(dto.getDiscountPercent());
            c.setDistanceLimit(dto.getDistanceLimit());
            c.setOverusePrice(dto.getOverusePrice());
            c.setRegularPrice(dto.getRegularPrice());
           // c.setOwner(dto.getOwner());

            pricingService.save(c);
            LOGGER.info("Action create pricing: {}] by user: {} successful", dto.getName(), p.getName());
            return ResponseEntity.ok().build();
        }catch (Exception e){
            LOGGER.error("Action create pricing: {}] by user: {} failed. Cause: {}", dto.getName(), p.getName(), e.getMessage());
        }
        return ResponseEntity.status(400).build();
    }

    @PreAuthorize("hasAuthority('ad_menagement_read')")
    @GetMapping(value="/pricing")
    public List<PricingDTO> getPricing(Principal p){
        List<PricingDTO> dto = new ArrayList<>();

        try {
            List<Pricing> c = pricingService.findAll();

            User user = userService.findByEmail(p.getName());

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
                    d.setOwner(a.getOwner().getEmail());
                    dto.add(d);
                }
            }

            LOGGER.info("Action get pricing by user: {} successful", p.getName());

        } catch (Exception e) {
            LOGGER.info("Action get pricing by user: {} failed. Cause: {}", p.getName(), e.getMessage());
        }

        return dto;
    }

    @PreAuthorize("hasAuthority('ad_menagement_write')")
    @PostMapping(value="/addCar")
    public ResponseEntity<?> addCar(@RequestBody CarDTO dto){

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

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
            LOGGER.info("Action add car: {} advertisement by user: {} successful", dto.getName(), user.getEmail());

            return ResponseEntity.ok().build();
        }catch (Exception e){
            LOGGER.error("Action add car: {} advertisement by user: {} failed. Cause: {}", dto.getName(), user.getEmail(), e.getMessage());
        }
        return ResponseEntity.status(400).build();
    }


    @PreAuthorize("hasAuthority('ad_menagement_read')")
    @GetMapping(value="/getCars")
    public List<CarDTO> getCars(Principal p){

        List<CarDTO> dto = new ArrayList<>();

       try {
           List<Cars> c = carsService.findAll();
           User user = userService.findByEmail(p.getName());

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

           LOGGER.info("Action get cars by user: {} successful", p.getName());

       } catch (Exception e) {
           LOGGER.error("Action get cars by user: {} failed. Cause: {}", p.getName(), e.getMessage());

       }

        return dto;
    }

    @PreAuthorize("hasAuthority('rent_menagement_write')")
    @PostMapping(value="/rentCar")
    public ResponseEntity<?> rentCar(@RequestBody RentRequestDTO dto){

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try{
            RentRequest rr = new RentRequest();
            Cars c = carsService.findByName(dto.getCarName());
            rr.setCarId(c);
            rr.setStartDate(dto.getStartDate());
            rr.setEndDate(dto.getEndDate());
            rr.setStatus(RequestStatus.PENDING);
            rr.setDeleted(false);
            rentRequestService.save(rr);

            LOGGER.info("Action rent a car: {} by user: {} successful", dto.getCarName(), user.getEmail());
            return ResponseEntity.ok().build();
        }catch (Exception e){
            LOGGER.error("Action rent a car: {} by user: {} failed. Cause: {}", dto.getCarName(), user.getEmail(), e.getMessage());
        }
        return ResponseEntity.status(400).build();
    }


    @PreAuthorize("hasAuthority('ad_menagement_read')")
    @GetMapping(value="/cars/top-rated")
    public List<CarsDetailsDTO> getTopRatedCars(Principal p){

        List<CarsDetailsDTO> dtos = new ArrayList<>();

        try {
            List<Cars> c = carsService.findAll();
            User user = userService.findByEmail(p.getName());

            for(Cars a : c) {
                if (a.getOwner().equals(user)) {
                    dtos.add(new CarsDetailsDTO(a));
                }
            }

            Collections.sort(dtos, new Comparator<CarsDetailsDTO>() {
                @Override
                public int compare(CarsDetailsDTO dto1, CarsDetailsDTO dto2) {
                    return dto2.getAverageRating().compareTo(dto1.getAverageRating());
                }
            });

            LOGGER.info("action=get top rated cars, user={}, result=success", p.getName());
        } catch (Exception e) {
            LOGGER.info("action=get top rated cars, user={}, result=failure, cause={}", p.getName(), e.getMessage());

        }

        return dtos;
    }


    @PreAuthorize("hasAuthority('ad_menagement_read')")
    @GetMapping(value="/cars/most-commented")
    public List<CarsDetailsDTO> getMostCommentedCars(Principal p){

        List<CarsDetailsDTO> dtos = new ArrayList<>();

        try {
            List<Cars> c = carsService.findAll();
            User user = userService.findByEmail(p.getName());

            for(Cars a : c) {
                if (a.getOwner().equals(user)) {
                    CarsDetailsDTO dto = new CarsDetailsDTO(a);
                    dto.setCommentsNumber(carsService.calculateCommentsNumber(a.getId()));
                    dtos.add(dto);
                }
            }

            Collections.sort(dtos, new Comparator<CarsDetailsDTO>() {
                @Override
                public int compare(CarsDetailsDTO dto1, CarsDetailsDTO dto2) {
                    return dto2.getCommentsNumber().compareTo(dto1.getCommentsNumber());
                }
            });

            LOGGER.info("action=get most commented cars, user={}, result=success", p.getName());
        } catch (Exception e) {
            LOGGER.info("action=get most commented cars, user={}, result=failure, cause={}", p.getName(), e.getMessage());

        }

        return dtos;
    }


    @PreAuthorize("hasAuthority('ad_menagement_read')")
    @GetMapping(value="/cars/highest-mileage")
    public List<CarsDetailsDTO> getHighestMileageCars(Principal p){

        List<CarsDetailsDTO> dtos = new ArrayList<>();

        try {
            List<Cars> c = carsService.findAll();
            User user = userService.findByEmail(p.getName());

            for(Cars a : c) {
                if (a.getOwner().equals(user)) {
                    CarsDetailsDTO dto = new CarsDetailsDTO(a);
                    dto.setMileageNumber(carsService.calculateMileageNumber(a.getId()));
                    dtos.add(dto);
                }
            }

            dtos.sort(new Comparator<CarsDetailsDTO>() {
                @Override
                public int compare(CarsDetailsDTO dto1, CarsDetailsDTO dto2) {
                    return Double.compare(dto2.getMileageNumber(), dto1.getMileageNumber());                }
            });

            LOGGER.info("action=get highest mileage cars, user={}, result=success", p.getName());
        } catch (Exception e) {
            LOGGER.info("action=get highest mileage cars, user={}, result=failure, cause={}", p.getName(), e.getMessage());

        }

        return dtos;
    }

}