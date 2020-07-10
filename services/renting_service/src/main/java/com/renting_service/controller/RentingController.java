package com.renting_service.controller;


import com.renting_service.dto.*;
import com.renting_service.model.*;
import com.renting_service.model.enums.RequestStatus;
import com.renting_service.repository.ReceiptArticleRepository;
import com.renting_service.service.*;
import com.renting_service.service.interfaces.RentRequestServiceInterface;
import com.renting_service.service.interfaces.UserServiceInterface;
import com.sun.xml.messaging.saaj.packaging.mime.MessagingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
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


            List<RecieptArticle> articles = reciept.getRecieptArticles();
            for(int i = 0; i < articles.size(); i++) {
                articles.get(i).setReciept(reciept);
                receiptArticleRepository.save(articles.get(i));
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
