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
import com.rent_a_car.agentski_bekend.service.CarsService;
import com.rent_a_car.agentski_bekend.service.interfaces.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.security.Principal;
import javax.ws.rs.core.MediaType;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@Validated
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

    @PreAuthorize("hasAuthority('ad_menagement_read')")
    @GetMapping(value = "cars")
    public ResponseEntity<List<CarsListingDTO>> getAllCars () {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try {
            ArrayList<CarsListingDTO> retVal = new ArrayList<CarsListingDTO>();
            for (Cars c : carsService.findAll()) {
                if (c.getId() != null) {
                    retVal.add(new CarsListingDTO(c));
                }
            }

            LOGGER.info("Action get all cars by user: {} successful", user.getEmail());
            return new ResponseEntity<List<CarsListingDTO>>(retVal, HttpStatus.OK);

        } catch (Exception e) {
            LOGGER.error("Action get all cars by user: {} failed. Cause: {}", user.getEmail(), e.getMessage());
        }

        return ResponseEntity.status(400).build();
    }

    @PreAuthorize("hasAuthority('ad_menagement_read')")
    @GetMapping(value = "mycars")
    public ResponseEntity<List<CarsListingDTO>> getMyCars (Principal p) {

        ArrayList<CarsListingDTO> retVal = new ArrayList<CarsListingDTO>();

        try{
            User user = userService.findByEmail(p.getName());
            for (Cars c : carsService.findAll()) {
                if (c.getId() != null) {
                    if(c.getOwner().getEmail().equals(user.getEmail())) {
                        retVal.add(new CarsListingDTO(c));
                    }
                }
            }
            LOGGER.info("Action get all user's cars by user: {} successful", p.getName());
            return new ResponseEntity<List<CarsListingDTO>>(retVal, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Action get all user's cars by user: {} failed. Cause: {}", p.getName(), e.getMessage());
        }

        return ResponseEntity.status(400).build();
    }

//    @GetMapping(value = "availableCars/{d1}/{d2}")
//    public List<CarsListingDTO> getAvailableCars (@PathVariable("d1") String d1, @PathVariable("d2") String d2, Principal p) throws ParseException {
//
//        DateFormat format = new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH);
//
//        Date startDate = format.parse(d1);
//        Date endDate = format.parse(d2);
//
//
////        Date startDate = d1;
////        Date endDate = d2;
//
//        List<RentRequest> rrList = rentRequestService.findAll();
//        List<Cars> cList = carsService.findAll();
//        List<CarsListingDTO> dto = new ArrayList<>();
//        List<CarsListingDTO> carsForRemoval = new ArrayList<>();
//        User u = userService.findByEmail(p.getName());
//        for (Cars c : cList) {
//            for (RentRequest rr : rrList) {
//                if (rr.getCarId().equals(c)) {
//                    if (startDate.before(rr.getStartDate())) {
//                        if(endDate.before(rr.getEndDate())) {
//                            //ubaci u dto
//                            CarsListingDTO dt = new CarsListingDTO(c);
//                            dto.add(dt);
//                        }
//                    }
//                    else if (startDate.after(rr.getStartDate())) {
//                        if (endDate.after(rr.getEndDate())){
//                            //ubaci u dto
//                            CarsListingDTO dt = new CarsListingDTO(c);
//                            dto.add(dt);
//                        }
//                    }
//                    else{
//                        CarsListingDTO dt = new CarsListingDTO(c);
//                        carsForRemoval.add(dt);
//                    }
//                }
//            }
//            //ubaci u dto
//            CarsListingDTO dt = new CarsListingDTO(c);
//            dto.add(dt);
//        }
//        for(CarsListingDTO ddd : carsForRemoval){
//            dto.remove(ddd);
//        }
//
//        return dto;
//
//    }

    private void canclePendingReservations(Date startDate, Date endDate, Integer id) {

        List<RentRequest> rrList = rentRequestService.findAll();

        for (RentRequest rr : rrList) {
            if (rr.getCarId().getId().equals(id)) {
                if (rr.getStatus().equals(RequestStatus.PENDING)) {
                    if (startDate.before(rr.getStartDate()) && endDate.after(rr.getStartDate())) {
                        rr.setStatus(RequestStatus.CANCELED);
                    }
                    if (startDate.after(rr.getStartDate()) && endDate.before(rr.getEndDate())) {
                        rr.setStatus(RequestStatus.CANCELED);
                    }
                    if (startDate.before(rr.getEndDate()) && endDate.after(rr.getEndDate())) {
                        rr.setStatus(RequestStatus.CANCELED);
                    }
                    rentRequestService.save(rr);
                }
            }
        }
    }
    @PreAuthorize("hasAuthority('ad_menagement_read')")
    @GetMapping(value = "availableCars/{d1}/{d2}/{town}")
    public List<CarsListingDTO> getAvailableCars (@PathVariable("d1") String d1, @PathVariable("d2") String d2, @PathVariable("town") String town,  Principal p) throws ParseException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = format.parse(d1);
        Date endDate = format.parse(d2);
        List<RentRequest> rrList = rentRequestService.findAll();
        List<Cars> cList = carsService.findAll();
        List<CarsListingDTO> dto = new ArrayList<>();
        List<CarsListingDTO> carsForRemoval = new ArrayList<>();
        User u = userService.findByEmail(p.getName());

        try {
            for (Cars c : cList) {
                if(c.getTown().equals(town)) {
                    for (RentRequest rr : rrList) {
                        if (rr.getStatus().equals(RequestStatus.RESERVED)) {
                            if (rr.getCarId().equals(c)) {
                                if (startDate.before(rr.getStartDate()) && endDate.after(rr.getStartDate())) {
                                    CarsListingDTO dt = new CarsListingDTO(c);
                                    carsForRemoval.add(dt);
                                }
                                if (startDate.after(rr.getStartDate()) && endDate.before(rr.getEndDate())) {
                                    CarsListingDTO dt = new CarsListingDTO(c);
                                    carsForRemoval.add(dt);
                                }
                                if (startDate.before(rr.getEndDate()) && endDate.after(rr.getEndDate())) {
                                    CarsListingDTO dt = new CarsListingDTO(c);
                                    carsForRemoval.add(dt);
                                }
                            }
                        }
                    }

                    CarsListingDTO dt = new CarsListingDTO(c);
                    dto.add(dt);
                }
            }
            for(int i = 0 ; i < dto.size() ; i++){
                for(CarsListingDTO ddd : carsForRemoval){
                    if(dto.get(i).getId().equals(ddd.getId())){
                        dto.remove(i);
                    }
                }
            }

            LOGGER.info("Action get all avaliable cars by user: {} successful ", p.getName());
            return dto;
        } catch (Exception e){
            LOGGER.error("Action get all avaliable cars by user: {} failed. Cause: {} ", p.getName(), e.getMessage());
        }

        return null;
    }





    @PreAuthorize("hasAuthority('ad_menagement_read')")
    @GetMapping(value = "filterCars/{fuelType}/{transType}/{manufac}/{carClass}/{carModel}")
    public List<CarsListingDTO> getFilteredCars (@PathVariable("fuelType") String fuelType, @PathVariable("transType") String transType, @PathVariable("manufac") String manufac, @PathVariable("carClass") String carClass, @PathVariable("carModel") String carModel, Principal p) throws ParseException {

        List<RentRequest> rrList = rentRequestService.findAll();
        List<Cars> cList = carsService.findAll();
        List<Cars> ftList = new ArrayList<>();
        List<Cars> ttList = new ArrayList<>();
        List<Cars> maList = new ArrayList<>();
        List<Cars> ccList = new ArrayList<>();
        List<Cars> cmList = new ArrayList<>();

        List<CarsListingDTO> dto = new ArrayList<>();
        List<CarsListingDTO> carsForRemoval = new ArrayList<>();
        User u = userService.findByEmail(p.getName());
        boolean ft;
        boolean tt;
        boolean ma;
        boolean cc;
        boolean cm;
        if(fuelType.equals("i")){
            ft = false;
        }else{
            ft = true;
        }
        if(transType.equals("i")){
            tt = false;
        }else{
            tt = true;
        }
        if(manufac.equals("i")){
            ma = false;
        }else{
            ma = true;
        }
        if(carClass.equals("i")){
            cc = false;
        }else{
            cc = true;
        }
        if(carModel.equals("i")){
            cm = false;
        }else{
            cm = true;
        }


        try {

//            for(Cars car:cList){
//                if(car.getModel().getName().equals(carModel)){
//                    cmList.add(car);
//                }
//                if(car.getModel().getCarClass().getName().equals(carClass)){
//                    ccList.add(car);
//                }
//                if(car.getModel().getTransmission().getName().equals(transType)){
//                    ttList.add(car);
//                }
//                if(car.getModel().getManufacturer().getName().equals(manufac)){
//                    maList.add(car);
//                }
//                if(car.getFuelType().getName().equals(fuelType)){
//                    ftList.add(car);
//                }
//            }
//
//            for(Cars cmc:cmList){
//                for(Cars ccc:ccList){
//                    for(Cars ttc:ttList){
//                        for(Cars mac:maList){
//                            for(Cars ftc:ftList){
//                                if(cmc.getId().equals(ccc.getId().equals(ttc.getId().equals(mac.getId().equals(ftc.getId()))))){
//                                    CarsListingDTO dsa = new CarsListingDTO(cmc);
//                                    dto.add(dsa);
//                                }
//                            }
//                        }
//                    }
//                }
//            }


            for(Cars car:cList){
                boolean uslov1 = false;
                boolean uslov2 = false;
                boolean uslov3 = false;
                boolean uslov4 = false;
                boolean uslov5 = false;
                if(!cm || car.getModel().getName().equals(carModel)){
                    uslov1 = true;
                }
                if(!cc || car.getModel().getCarClass().getName().equals(carClass)){
                    uslov2 = true;
                }
                if(!tt || car.getModel().getTransmission().getName().equals(transType)){
                    uslov3 = true;
                }
                if(!ma || car.getModel().getManufacturer().getName().equals(manufac)){
                    uslov4 = true;
                }
                if(!ft || car.getFuelType().getName().equals(fuelType)){
                    uslov5 = true;
                }
                if(uslov1 && uslov2 && uslov3 && uslov4 && uslov5){
                    CarsListingDTO dsa = new CarsListingDTO(car);
                    dto.add(dsa);
                }

            }

//            for(Cars car:cList){
//                if(car.getModel().getName().equals(carModel) && car.getModel().getCarClass().getName().equals(carClass) && car.getModel().getTransmission().getName().equals(transType) && car.getModel().getManufacturer().getName().equals(manufac) && car.getFuelType().getName().equals(fuelType)){
//                    CarsListingDTO dsa = new CarsListingDTO(car);
//                    dto.add(dsa);
//                }
//            }

            LOGGER.info("Action get all avaliable cars by user: {} successful ", p.getName());
            return dto;
        } catch (Exception e){
            LOGGER.error("Action get all avaliable cars by user: {} failed. Cause: {} ", p.getName(), e.getMessage());
        }

        return null;
    }






    @PreAuthorize("hasAuthority('ad_menagement_read')")
    @GetMapping(value = "get/{t}")
    public ResponseEntity<List<CarsListingDTO>> filterCarsByTown (@PathVariable("t") String t) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ArrayList<CarsListingDTO> retVal = new ArrayList<CarsListingDTO>();

        try {
            for (Cars c : carsService.filterByCity((ArrayList<Cars>) carsService.findAll(), t.replaceAll("_", " "))) {
                retVal.add(new CarsListingDTO(c));
            }

            LOGGER.info("Action filter cars by town by user: {} successful", user.getEmail());
            return new ResponseEntity<List<CarsListingDTO>>(retVal, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Action filter cars by town by user: {} failed. Cause: {}", user.getEmail(), e.getMessage());
        }

        return new ResponseEntity<List<CarsListingDTO>>(retVal, HttpStatus.BAD_REQUEST);

    }


    @PreAuthorize("hasAuthority('rent_menagement_write')")
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

            LOGGER.info("Action rent a car by user: {} successful", p.getName());
            return ResponseEntity.ok().build();

        }catch (Exception e){
            LOGGER.info("Action rent a car by user: {} failed. Cause: {}", p.getName(), e.getMessage());
        }

        return ResponseEntity.status(400).build();
    }

    @PreAuthorize("hasAuthority('rent_menagement_write')")
    @PostMapping(value="/makeReservation")
    public ResponseEntity<?> makeReservation(@RequestBody RentRequestDTO dto, Principal p){

        User user = userService.findByEmail(p.getName());

        try{

            RentRequest rr = new RentRequest();
            Cars c = carsService.findByName(dto.getCarName());
            rr.setCarId(c);
            rr.setStartDate(dto.getStartDate());
            rr.setEndDate(dto.getEndDate());
            rr.setStatus(RequestStatus.RESERVED);
            rr.setDeleted(false);
            rr.setOwningUser(null);
            rentRequestService.save(rr);
            canclePendingReservations(dto.getStartDate(), dto.getEndDate(), rr.getCarId().getId());
            LOGGER.info("Action rent a car by user: {} successful", p.getName());
            return ResponseEntity.ok().build();

        }catch (Exception e){
            LOGGER.info("Action rent a car by user: {} failed. Cause: {}", p.getName(), e.getMessage());
        }

        return ResponseEntity.status(400).build();
    }


    @PreAuthorize("hasAuthority('ad_menagement_read')")
    @GetMapping (value = "cars/{id}")
    public ResponseEntity<CarsDetailsDTO> getOneCar (@PathVariable("id") Integer id) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try {

            CarsDetailsDTO retVal = new CarsDetailsDTO(carsService.getCar(id));

            LOGGER.info("Action get car id:{} by user: {} successful", id, user.getEmail());
            return new ResponseEntity<CarsDetailsDTO>(retVal, HttpStatus.OK);

        } catch(Exception e) {
            LOGGER.error("Action get car id:{} by user: {} failed. Cause:{}", id, user.getEmail(), e.getMessage());
        }

        return ResponseEntity.status(400).build();
    }

    @PreAuthorize("hasAuthority('rent_menagement_read')")
    @GetMapping(value = "requests")
    public ResponseEntity<List<RentRequestDTO>> getAllRentRequests (@RequestParam(value = "status", required = false) String status) {

        ArrayList<RentRequestDTO> retVal = new ArrayList<RentRequestDTO>();
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

       try {
           if(status==null){
               for(RentRequest rr : rentRequestService.findAll()){
                   retVal.add(new RentRequestDTO(rr));
               }
               LOGGER.info("Action get all rent requests by user: {} successful", user.getEmail());

           } else if (status.equals("paid")){
               for(RentRequest rr : rentRequestService.findAll()){
                   if(rr.getStatus().equals(RequestStatus.PAID) && rr.getCarId().getOwner().getEmail().equals(user.getEmail()))
                       retVal.add(new RentRequestDTO(rr));
               }
               LOGGER.info("Action get paid rent requests by user: {} successful", user.getEmail());
           }

           return new ResponseEntity<List<RentRequestDTO>>(retVal, HttpStatus.OK);

       } catch (Exception e) {
           LOGGER.error("Action get paid rent requests by user: {} failed. Cause: {}", user.getEmail(), e.getMessage());
       }

       return ResponseEntity.status(400).build();

    }


    @PreAuthorize("hasAuthority('rent_menagement_read')")
    @GetMapping(value = "payRequests")
    public List<RentRequestDTO> getRequestsForPayment (Principal p) {

        List<RentRequestDTO> dto = new ArrayList<>();
        List<RentRequest> rrl = rentRequestService.findAll();
        User user = userService.findByEmail(p.getName());

        try {
            for(RentRequest rr : rrl){
                if (rr.getStatus().equals(RequestStatus.RESERVED)) {
                    if (rr.getOwningUser().getEmail().equals(user.getEmail())) {
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

            LOGGER.info("Action get rent request for payment of user: {} successful", p.getName());
            return dto;

        } catch (Exception e) {
            LOGGER.error("Action get rent request for payment of user: {} failed. Cause: {}", p.getName(), e.getMessage());
        }
        return null;
    }


    @PreAuthorize("hasAuthority('rent_menagement_write')")
    @PostMapping(value = "payRequests")
    public ResponseEntity<?> payRent (@RequestBody @Min(1) @Max(100000) Integer id) {

        RentRequest rrl = rentRequestService.findById(id);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try {
           rrl.setStatus(RequestStatus.PAID);
           rentRequestService.save(rrl);

           LOGGER.info("Action pay renting car: {} by user: {} successful", user.getEmail(), rrl.getCarId().getName());
           return ResponseEntity.ok().build();

       } catch (Exception e) {
           LOGGER.info("Action pay renting car: {} by user: {} failed. Cause: {}", user.getEmail(), rrl.getCarId().getName(), e.getMessage());
       }

        return ResponseEntity.status(400).build();
    }


//    @GetMapping(value = "rentRequests")
//    public List<RentRequestDTO> getRentRequests (Principal p) {
//        List<RentRequest> retVal = rentRequestService.findAll();
//        List<RentRequestDTO> dto = new ArrayList<>() ;
//        User user = userService.findByEmail(p.getName());
//        for (RentRequest c : retVal) {
//            if(c.getCarId().getOwner().equals(user)) {
//                RentRequestDTO dto1 = new RentRequestDTO();
//                dto1.setId(c.getId());
//                dto1.setStartDate(c.getStartDate());
//                dto1.setEndDate(c.getEndDate());
//                if (c.getStatus().equals(RequestStatus.PENDING)) {
//                    dto1.setStatus("PENDING");
//                } else if (c.getStatus().equals(RequestStatus.CANCELED)) {
//                    dto1.setStatus("CANCELED");
//                } else if (c.getStatus().equals(RequestStatus.PAID)) {
//                    dto1.setStatus("PAID");
//                } else if (c.getStatus().equals(RequestStatus.RESERVED)) {
//                    dto1.setStatus("RESERVED");
//                } else if (c.getStatus().equals(RequestStatus.RETURNED)) {
//                    dto1.setStatus("RETURNED");
//                }
//                dto.add(dto1);
//            }
//        }
//        return dto;
//    }


    @PreAuthorize("hasAuthority('rent_menagement_read')")
    @GetMapping(value = "requests/group/{id}")
    public ResponseEntity<List<RentRequestDTO>> getGroupRequests (@PathVariable("id") @Min(1) @Max(100000) Integer groupId) {   // URL input param valid.

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ArrayList<RentRequestDTO> retVal = new ArrayList<RentRequestDTO>();

        try {

            for (RentRequest rr : rentRequestService.findAll()) {
                if(rr.getRequestGroupId().equals(groupId))
                    retVal.add(new RentRequestDTO(rr));
            }

            LOGGER.info("Action get rent requests bundle by user: {} successful", user.getEmail());
            return new ResponseEntity<List<RentRequestDTO>>(retVal, HttpStatus.OK);

        } catch (Exception e) {
            LOGGER.info("Action get rent requests bundle by user: {} failed. Cause: {}", user.getEmail(), e.getMessage());
        }

        return new ResponseEntity<List<RentRequestDTO>>(retVal, HttpStatus.BAD_REQUEST);

    }


    @PreAuthorize("hasAuthority('review_menagement_write')")
    @PostMapping(value ="report", consumes = MediaType.APPLICATION_JSON)
    public ResponseEntity<?> addRentingReport(@RequestBody RentingReportDTO dto) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

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

            LOGGER.info("Action add renting report for request id:{} by user: {} successful", dto.getRentingInstanceId(), user.getEmail());
            return ResponseEntity.status(200).build();

        } catch(Exception e) {

            LOGGER.error("Action add renting report for request id:{} by user: {} failed. Cause: {}", dto.getRentingInstanceId(), user.getEmail(), e.getMessage());

        }
        return ResponseEntity.status(400).build();

    }


    @PreAuthorize("hasAuthority('rent_menagement_read')")
    @GetMapping(value = "rentRequests")
    public List<RentRequestDTO> getRentRequests (Principal p) {

        List<RentRequest> retVal = rentRequestService.findAll();
        List<RentRequestDTO> dto = new ArrayList<>();
        User user = userService.findByEmail(p.getName());

        try {

            for (RentRequest c : retVal) {
                if (c.getCarId().getOwner().equals(user)) {

                    if (c.getStatus().equals(RequestStatus.PENDING)) {
                        RentRequestDTO dto1 = new RentRequestDTO();
                        dto1.setId(c.getId());
                        dto1.setStartDate(c.getStartDate());
                        dto1.setEndDate(c.getEndDate());
                        dto1.setCarName(c.getCarId().getName());
                        dto1.setStatus("PENDING");
                        dto.add(dto1);
                    }
                    //dto.add(dto1);
                }
            }

            LOGGER.info("Action get all rent requests by user: {} successful", p.getName());
            return dto;

        } catch (Exception e) {
            LOGGER.error("Action get all rent requests by user: {} failed. Cause: {}", p.getName(), e.getMessage());
        }

        return null;
    }


    @PreAuthorize("hasAuthority('review_menagement_write')")
    @PostMapping(value="review", consumes = MediaType.APPLICATION_JSON)
    public ResponseEntity<?> addReview(@RequestBody CarReviewDTO dto, Principal p){

        try{

            CarReview review = new CarReview();
            review.setDeleted(false);
            review.setApproved(false);
            review.setRating(dto.getRating());
            review.setReview(dto.getReview());
            Cars car = carsService.getCar(dto.getCarId());
            review.setCar(car);

            User user = userService.findByEmail(p.getName());
            review.setReviewer(user);

            List<RentRequest> usersRequests = userService.findUsersRentRequests(user.getEmail());

            for(RentRequest rr : usersRequests){
                if(rr.getCarId().getId() == dto.getCarId() && rr.getStatus().equals(RequestStatus.RETURNED)){
                    carReviewService.save(review);
                    LOGGER.info("User: {} posted a review for car id:{} successfully", p.getName(), dto.getCarId());
                    return ResponseEntity.status(200).build();
                }
            }

            LOGGER.warn("User: {} is not allowed to post a review for car id:{} ", p.getName(), dto.getCarId());
            return ResponseEntity.status(403).build();

        }catch (Exception e){
            LOGGER.error("Action add car review for car id:{} by user: {} failed. Cause: {}", dto.getCarId(), p.getName(), e.getMessage());
        }
        return ResponseEntity.status(400).build();
    }


    @PreAuthorize("hasAuthority('review_menagement_read')")
    @GetMapping(value = "reviews/cars/{id}")
    public ResponseEntity<List<CarReviewDTO>> getAllCarReviews (@PathVariable("id") Integer carId) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<CarReviewDTO> retVal = new ArrayList<CarReviewDTO>();
        Cars car = carsService.getCar(carId);

        try {

            for(CarReview cr : car.getReviews()){
                if(cr.isApproved())
                    retVal.add(new CarReviewDTO(cr));
            }

            LOGGER.info("Action get all car reviews for car id: {} by user: {} successful ", carId, user.getEmail());
            return new ResponseEntity<List<CarReviewDTO>>(retVal, HttpStatus.OK);

        } catch (Exception e) {
            LOGGER.error("Action get all car reviews for car id: {} by user: {}  failed. Cause: {} ", carId, user.getEmail(), e.getMessage());
        }

        return ResponseEntity.status(400).build();

    }


    @PreAuthorize("hasAuthority('rent_menagement_write')")
    @PostMapping(value="approveRentRequest")
    public ResponseEntity<?> approveRentRequest(@RequestBody Integer id){

        RentRequest u = rentRequestService.findById(id);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try {

            u.setStatus(RequestStatus.RESERVED);
            rentRequestService.save(u);
            // todo odbiti koji se preklapaju
            //  carsService.autoReject(u);
            canclePendingReservations(u.getStartDate(), u.getEndDate(), u.getCarId().getId());
            LOGGER.info("Action approve rent request id: {} by user: {} successful", id.toString(), user.getEmail());
            return ResponseEntity.ok().build();

         } catch (Exception e) {
             LOGGER.error("Action approve rent request id: {} by user: {} failed. Cause: {}", id.toString(), user.getEmail(),e.getMessage());
        }

        return ResponseEntity.status(400).build();
    }

    @PreAuthorize("hasAuthority('rent_menagement_write')")
    @PostMapping(value="rejectRentRequest")
    public ResponseEntity<?> rejectRentRequest(@RequestBody Integer id){

        RentRequest u = rentRequestService.findById(id);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try {
            u.setStatus(RequestStatus.CANCELED);
            rentRequestService.save(u);

            LOGGER.info("Action reject rent request id: {} by user: {} successful", id.toString(), user.getEmail());
            return ResponseEntity.ok().build();

        } catch (Exception e) {
            LOGGER.error("Action reject rent request id: {} by user: {} failed. Cause: {}", id.toString(), user.getEmail(), e.getMessage());
        }

        return ResponseEntity.status(400).build();
    }


}
