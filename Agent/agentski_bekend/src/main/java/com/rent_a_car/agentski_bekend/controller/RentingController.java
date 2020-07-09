package com.rent_a_car.agentski_bekend.controller;

import com.rent_a_car.agentski_bekend.dto.*;
import com.rent_a_car.agentski_bekend.model.*;
import com.rent_a_car.agentski_bekend.model.enums.RequestStatus;
import com.rent_a_car.agentski_bekend.repository.ReceiptArticleRepository;
import com.rent_a_car.agentski_bekend.service.*;

import com.rent_a_car.agentski_bekend.service.interfaces.RentRequestServiceInterface;
import com.sun.xml.messaging.saaj.packaging.mime.MessagingException;
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
import java.io.IOException;
import java.security.Principal;
import javax.ws.rs.core.MediaType;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@Validated
@RequestMapping(value = "api/renting/")
public class RentingController {

    @Autowired
    CarsService carsService;

    @Autowired
    UserServiceInterface userService;

    @Autowired
    RentRequestServiceInterface rentRequestService;

    @Autowired
    RentingReportService rentingReportService;

    @Autowired
    CarReviewService carReviewService;

    @Autowired
    MailService mailService;

    @Autowired
    ReceiptService receiptService;

    @Autowired
    ReceiptArticleRepository receiptArticleRepository;


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

            LOGGER.info("action=get cars, user={}, result=success", user.getEmail());
            return new ResponseEntity<>(retVal, HttpStatus.OK);

        } catch (Exception e) {
            LOGGER.error("action=get cars, user={}, result=failure,  cause={}", user.getEmail(), e.getMessage());
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
            LOGGER.info("action=get cars, user={}, result=success", user.getEmail());
            return new ResponseEntity<List<CarsListingDTO>>(retVal, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("action=get cars, user={}, result=failure,  cause={}", p.getName(), e.getMessage());
        }

        return ResponseEntity.status(400).build();
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

            LOGGER.info("action=get available cars, user={}, result=success", p.getName());
            return dto;
        } catch (Exception e){
            LOGGER.error("action=get available cars, user={}, result=failure,  cause={}", p.getName(), e.getMessage());
        }

        return null;
    }





    @PreAuthorize("hasAuthority('rent_menagement_write')")
    @PostMapping(value="/rentCar")
    public ResponseEntity<?> rentCar(@RequestBody RentRequestDTO dto, Principal p){

        User user = userService.findByEmail(p.getName());
        if(user.isRentRBan()){
            return ResponseEntity.status(403).build();
        }

        for(RentRequest rr : user.getRentRequests()){
            if(rr.getStatus().equals(RequestStatus.RETURNED))
                return ResponseEntity.status(403).build();
        }

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

            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            if(rr.getStatus().equals(RequestStatus.PENDING)) {
                                rr.setStatus(RequestStatus.CANCELED);
                                rentRequestService.save(rr);
                            }

                        }
                    },
                    24 * 3600 * 1000
            );

            LOGGER.info("action=rent a car, user={}, result=success", user.getEmail());
            return ResponseEntity.ok().build();

        }catch (Exception e){
            LOGGER.error("action=rent a car, user={}, result=failure,  cause={}", user.getEmail(), e.getMessage());
        }

        return ResponseEntity.status(400).build();
    }


    @PreAuthorize("hasAuthority('ad_menagement_read')")
    @GetMapping (value = "cars/{id}")
    public ResponseEntity<CarsDetailsDTO> getOneCar (@PathVariable("id") Integer id) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try {

            CarsDetailsDTO retVal = new CarsDetailsDTO(carsService.getCar(id));

            LOGGER.info("action=get car, user={}, result=success", user.getEmail());
            return new ResponseEntity<>(retVal, HttpStatus.OK);

        } catch(Exception e) {
            LOGGER.error("action=get car, user={}, result=failure,  cause={}", user.getEmail(), e.getMessage());
        }

        return ResponseEntity.status(400).build();
    }

    @PreAuthorize("hasAuthority('rent_menagement_read')")
    @GetMapping(value = "requests")
    public ResponseEntity<List<RentRequestDTO>> getAllRentRequests (@RequestParam(value = "status", required = false) String status) {

        ArrayList<RentRequestDTO> retVal = new ArrayList<>();
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

       try {
           if(status==null){
               for(RentRequest rr : rentRequestService.findAll()){
                   retVal.add(new RentRequestDTO(rr));
               }

           } else if (status.equals("paid")){
               for(RentRequest rr : rentRequestService.findAll()){
                   if(rr.getStatus().equals(RequestStatus.PAID) && rr.getCarId().getOwner().getEmail().equals(user.getEmail()))
                       retVal.add(new RentRequestDTO(rr));
               }
           } else if (status.equals("pending")){
               for(RentRequest rr : rentRequestService.findAll()){
                   if(rr.getStatus().equals(RequestStatus.PENDING) && rr.getCarId().getOwner().getEmail().equals(user.getEmail()))
                       retVal.add(new RentRequestDTO(rr));
               }
           } else if (status.equals("reserved")){
               for(RentRequest rr : rentRequestService.findAll()){
                   if(rr.getStatus().equals(RequestStatus.RESERVED) && rr.getCarId().getOwner().getEmail().equals(user.getEmail()))
                       retVal.add(new RentRequestDTO(rr));
               }
           }

           LOGGER.info("action=get rent requests, user={}, result=success", user.getEmail());
           return new ResponseEntity<>(retVal, HttpStatus.OK);

       } catch (Exception e) {
           LOGGER.error("action=get rent requests, user={}, result=failure,  cause={}", user.getEmail(), e.getMessage());
       }

       return ResponseEntity.status(400).build();

    }


    @PreAuthorize("hasAuthority('rent_menagement_read')")
    @GetMapping(value = "receipts")
    public List<ReceiptDTO> getReceipts (Principal p) {

        List<ReceiptDTO> dtos = new ArrayList<>();
        User user = userService.findByEmail(p.getName());

        List<RentRequest> reqs = user.getRentRequests();

        try {

            for(RentRequest rr : reqs){
                if(rr.getStatus().equals(RequestStatus.RETURNED)){

                    ReceiptDTO dto = new ReceiptDTO(rr.getReceipt());
                    dto.setCarName(rr.getCarId().getName());
                    dto.setDiscount(rr.getCarId().getPricing().getDiscountPercent());
                    dto.setCarId(rr.getCarId().getId());
                    dtos.add(dto);

                }
            }

            LOGGER.info("action=get receipts, user={}, result=success", user.getEmail());
        } catch (Exception e) {
            LOGGER.error("action=get receipts, user={}, result=failure,  cause={}", user.getEmail(), e.getMessage());
        }

        return dtos;
    }


    @PreAuthorize("hasAuthority('rent_menagement_write')")
    @PutMapping(value = "requests/{id}")
    public ResponseEntity<?> payRent (@PathVariable @Min(1) @Max(100000) Integer id) {

        RentRequest rrl = rentRequestService.findById(id);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try {

           rrl.setStatus(RequestStatus.PAID);
           rentRequestService.save(rrl);

           LOGGER.info("action=pay rent request, user={}, result=success", user.getEmail());
           return ResponseEntity.ok().build();

       } catch (Exception e) {
            LOGGER.error("action=pay rent request, user={}, result=failure,  cause={}", user.getEmail(), e.getMessage());
       }

        return ResponseEntity.status(400).build();
    }





    @PreAuthorize("hasAuthority('rent_menagement_read')")
    @GetMapping(value = "requests/group/{id}")
    public ResponseEntity<List<RentRequestDTO>> getGroupRequests (@PathVariable("id") @Min(1) @Max(100000) Integer groupId) {   // URL input param valid.

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ArrayList<RentRequestDTO> retVal = new ArrayList<>();

        try {

            for (RentRequest rr : rentRequestService.findAll()) {
                if(rr.getRequestGroupId().equals(groupId))
                    retVal.add(new RentRequestDTO(rr));
            }

            LOGGER.info("action=get bundle, user={}, result=success", user.getEmail());
            return new ResponseEntity<>(retVal, HttpStatus.OK);

        } catch (Exception e) {
            LOGGER.error("action=get bundle, user={}, result=failure,  cause={}", user.getEmail(), e.getMessage());
        }

        return new ResponseEntity<>(retVal, HttpStatus.BAD_REQUEST);

    }


    @PreAuthorize("hasAuthority('review_menagement_write')")
    @PostMapping(value ="report", consumes = MediaType.APPLICATION_JSON)
    public ResponseEntity<?> addRentingReport(@RequestBody RentingReportDTO dto) throws MessagingException, IOException, javax.mail.MessagingException {

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

            Cars car = carsService.getCar(req.getCarId().getId());
            car.setMilage(car.getMilage() + report.getAddedMileage());
            carsService.save(car);

            Reciept reciept = new Reciept();
            reciept.setCustomer(req.getOwningUser());
            reciept.setDeleted(false);
            reciept.setOwner(user);
            reciept.setRecieptArticles(receiptService.generateReceiptArticles(req));
            reciept.setSum(receiptService.calculateSum(reciept.getRecieptArticles()));

            for(RecieptArticle ra: reciept.getRecieptArticles()) {
                ra.setReciept(reciept);
                receiptArticleRepository.save(ra);
            }

            receiptService.save(reciept);

            req.setReceipt(reciept);
            rentRequestService.save(req);

            String text = "Dear sir/madam, " + '\n';
            text += "The renting receipt for car " + car.getName() + " has been added to your account. Total ammount is " + reciept.getSum() +". " + '\n';
            text += "You won't be able to rent cars until the sum has been paid.";
            text +=  "\n\n\n" + "Sincerely, Rent a car support team.";
            mailService.sendEmail(req.getOwningUser().getEmail(), "Receipt has been added to your account", text);

            LOGGER.info("action=add renting report, user={}, result=success", user.getEmail());
            return ResponseEntity.status(200).build();

        } catch(Exception e) {

            LOGGER.error("action=add renting report, user={}, result=failure,  cause={}", user.getEmail(), e.getMessage());

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

            LOGGER.info("action=get rent requests, user={}, result=success", user.getEmail());
            return dto;

        } catch (Exception e) {
            LOGGER.error("action=get rent requests, user={}, result=failure,  cause={}", user.getEmail(), e.getMessage());
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

                if( rr.getCarId().getId() == dto.getCarId() &&  rr.getEndDate().before(new Date()) ){
                    carReviewService.save(review);
                    LOGGER.info("action=add car review, user={}, result=success", user.getEmail());
                    return ResponseEntity.status(200).build();
                }
            }

            List<Cars> ads = carsService.findAll();

            for(Cars c : ads){
                if(c.getOwner().getEmail().equals(p.getName())){
                    carReviewService.save(review);
                    LOGGER.info("action=add comment, user={}, result=success", user.getEmail());
                    return ResponseEntity.status(200).build();
                }
            }

            LOGGER.warn("action=get rent requests, user={}, result=warning,  cause=User is not allowed to post a review for this car.", user.getEmail());
            return ResponseEntity.status(403).build();

        }catch (Exception e){
            LOGGER.error("action=get rent requests, user={}, result=failure,  cause={}", p.getName(), e.getMessage());
        }
        return ResponseEntity.status(400).build();
    }


    @PreAuthorize("hasAuthority('review_menagement_read')")
    @GetMapping(value = "reviews/cars/{id}")
    public ResponseEntity<List<CarReviewDTO>> getAllCarReviews (@PathVariable("id") Integer carId) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<CarReviewDTO> retVal = new ArrayList<>();
        Cars car = carsService.getCar(carId);

        try {

            for(CarReview cr : car.getReviews()){
                if(cr.isApproved())
                    retVal.add(new CarReviewDTO(cr));
            }

            LOGGER.info("action=get car reviews, user={}, result=success", user.getEmail());
            return new ResponseEntity<>(retVal, HttpStatus.OK);

        } catch (Exception e) {
            LOGGER.error("action=get car reviews, user={}, result=failure,  cause={}", user.getEmail(), e.getMessage());
        }

        return ResponseEntity.status(400).build();

    }


    @PreAuthorize("hasAuthority('rent_menagement_write')")
    @PostMapping(value="approveRentRequest")
    public ResponseEntity<?> approveRentRequest(@RequestBody Integer id){

        RentRequest u = rentRequestService.findById(id);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<RentRequest> autoCanceled = new ArrayList<>();

        try {

            u.setStatus(RequestStatus.RESERVED);
            rentRequestService.save(u);

            List<RentRequest> requests = rentRequestService.findAll();
            for(RentRequest rr : requests) {
                if(rr.getCarId().getId() == u.getCarId().getId() && rr.getStatus().equals(RequestStatus.PENDING)) {
                    rr.setStatus(RequestStatus.CANCELED);
                    rentRequestService.save(rr);
                    autoCanceled.add(rr);
                }
            }


            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            if(!u.getStatus().equals(RequestStatus.PAID)) {
                                u.setStatus(RequestStatus.CANCELED);
                                rentRequestService.save(u);

                                for(RentRequest rr : autoCanceled) {
                                    if (rr.getStartDate().before(u.getEndDate()) && u.getEndDate().after(rr.getStartDate())) {
                                        rr.setStatus(RequestStatus.PENDING);
                                        rentRequestService.save(rr);                                    }

                                }

                                //TODO uraditi isto za bundle odbijanje

                            }

                        }
                    },
                    12 * 3600 * 1000
            );


//            rentRequestService.canclePendingReservations(u.getStartDate(), u.getEndDate(), u.getCarId().getId());

            LOGGER.info("action=approve rent requests, user={}, result=success", user.getEmail());
            return ResponseEntity.ok().build();

         } catch (Exception e) {
            LOGGER.error("action=approve rent requests, user={}, result=failure,  cause={}", user.getEmail(), e.getMessage());
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

            LOGGER.info("action=reject rent requests, user={}, result=success", user.getEmail());
            return ResponseEntity.ok().build();

        } catch (Exception e) {
            LOGGER.error("action=reject rent requests, user={}, result=failure,  cause={}", user.getEmail(), e.getMessage());
        }

        return ResponseEntity.status(400).build();
    }


    @PreAuthorize("hasAuthority('rent_menagement_read')")
    @GetMapping(value = "requests/history")
    public ResponseEntity<List<RentRequestDTO>> getRentRequestHistory (Principal p) {

        List<RentRequestDTO> dtos = new ArrayList<>();
        User user = userService.findByEmail(p.getName());
        List<RentRequest> requests = user.getRentRequests();

        try {

            for( RentRequest rr : requests){
                dtos.add(new RentRequestDTO(rr));
            }

            LOGGER.info("action=get rent request history, user={}, result=success", p.getName());
            return new ResponseEntity<>(dtos, HttpStatus.OK);

        } catch (Exception e) {
            LOGGER.error("action=get rent request history, user={}, result=failure, cause={}", p.getName(), e.getMessage());
        }

        return new ResponseEntity<>(dtos, HttpStatus.BAD_REQUEST);
    }


    @PreAuthorize("hasAuthority('rent_menagement_write')")
    @PostMapping(value="requests/{id}/cancel")
    public ResponseEntity<?> cancelRentRequest(@PathVariable("id") Integer id, Principal p){

        try {

            RentRequest rr = rentRequestService.findById(id);
            rr.setStatus(RequestStatus.CANCELED);

            String text = "Dear sir/madam, " + '\n';
            text += "user " + p.getName() + " cancelled rent request for car" + rr.getCarId().getName() + ".";
            text +=  "\n\n\n" + "Sincerely, Rent a car support team.";
            mailService.sendEmail(rr.getCarId().getOwner().getEmail(), "Rent request cancelled!", text);

            rentRequestService.save(rr);

            LOGGER.info("action=cancel rent request, user={}, result=success", p.getName());
            return ResponseEntity.ok().build();

        } catch (Exception e) {
            LOGGER.error("action=cancel rent request, user={}, result=failure, cause={}", p.getName(), e.getMessage());
        }

        return ResponseEntity.status(400).build();
    }



}
