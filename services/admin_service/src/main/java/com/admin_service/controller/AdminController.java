package com.admin_service.controller;


import com.admin_service.dto.*;
import com.admin_service.model.*;
import com.admin_service.service.CarsService;
import com.admin_service.service.MailService;
import com.admin_service.service.UserRequestService;
import com.admin_service.service.interfaces.*;
import com.sun.xml.messaging.saaj.packaging.mime.MessagingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@Validated
//@RequestMapping(value = "api/admin/")
public class AdminController {

    @Autowired
    CarsService carsService;

    @Autowired
    CarClassServiceInterface carClassService;

    @Autowired
    ManufacturerServiceInterface manufacturerService;

    @Autowired
    TransmissionTypeServiceInterface transmissionTypeService;

    @Autowired
    FuelTypeServiceInterface fuelTypeService;

    @Autowired
    CarModelsServiceInterface carModelsService;

    @Autowired
    UserRequestServiceInterface userRequestService;

    @Autowired
    UserServiceInterface userService;

    @Autowired
    CarReviewServiceInterface carReviewService;

    @Autowired
    CompanyServiceInterface companyService;

    @Autowired
    RoleServiceInterface roleService;

    @Autowired
    PrivilegeServiceInterface privilegeService;

    @Autowired
    CarsService carService;

    @Autowired
    MailService mailService;

    @Value("${back-uri}")
    private String uri;

    private static final Logger LOGGER = LogManager.getLogger(AdminController.class.getName());


    @PostMapping(value="/privilege/{id}")
    public ResponseEntity<?> privilege(@RequestBody String email, @PathVariable("id") Integer id) {

        User user = userService.findByEmail(email);
        try {


            if(id == 1){
                user.setAdBan(!user.isAdBan());
            }

            if(id == 2){
                user.setRentRBan(!user.isRentRBan());
            }

            if(id == 3){
                user.setMessageRBan(!user.isMessageRBan());
            }
            userService.save(user);
            LOGGER.info("Action approve car review id:{} by user: {} successful", id.toString(), user.getEmail());
            return ResponseEntity.ok().build();

        } catch (Exception e) {
            LOGGER.error("Action approve car review id:{} by user: {} failed. Cause: {}", id.toString(), user.getEmail(), e.getMessage());
        }
        return ResponseEntity.status(400).build();

    }

    @PreAuthorize("hasAuthority('review_menagement_read')")
    @GetMapping(value="/carReviews")
    public List<CarReviewDTO> getCarReviews(){

        List<CarReview> cr = carReviewService.findAll();
        List<CarReviewDTO> dto = new ArrayList<>();
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try {

            for(CarReview c : cr){
                if(!c.isApproved()) {
                    CarReviewDTO crDTO = new CarReviewDTO();
                    crDTO.setId(c.getId());
                    crDTO.setReviewerId(c.getReviewer().getId());
                    crDTO.setCarId(c.getCar().getId());
                    crDTO.setRating(c.getRating());
                    crDTO.setReview(c.getReview());
                    crDTO.setApprovedDate(c.getApprovedDate());
                    crDTO.setDeleted(c.isDeleted());
                    crDTO.setApproved(c.isApproved());
                    dto.add(crDTO);
                }
            }

            LOGGER.info("action=get car reviews, user={}, result=success", user.getEmail());
            return dto;

        } catch (Exception e) {
            LOGGER.error("action=get car reviews, user={}, result=failure, cause={}", user.getEmail(), e.getMessage());
        }

        return null;
    }

    @PreAuthorize("hasAuthority('review_menagement_write')")
    @PostMapping(value="/carReviews")
    public ResponseEntity<?> approveReview(@RequestBody @Min(1) @Max(100000)Integer id){

        List<CarReview> crList = carReviewService.findAll();
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try {

            for(CarReview cr : crList){
                if(cr.getId() == id){
                    cr.setApproved(true);
                    cr.setApprovedDate(new Date());
                    carReviewService.save(cr);
                    Cars car = carService.getCar(cr.getCar().getId());
                    car.getReviews().add(cr);
                    car.setAverageRating(carsService.calculateAverageRating(car.getId()));
                    carService.save(car);

                    String text = "Dear sir/madam, " + '\n';
                    text += "Your car review for" + cr.getCar().getName() + "has been approved by our administrator staff. \n ";
                    text += "\n\n\n" + "Sincerely, Rent a car support team.";
                    mailService.sendEmail(cr.getReviewer().getEmail(), text, "Car review has been approved");

                }
            }

            LOGGER.info("action=approve car review, user={}, result=success", user.getEmail());
            return ResponseEntity.ok().build();

        } catch (Exception e) {
            LOGGER.error("action=approve car review, user={}, result=failure, cause={}", user.getEmail(), e.getMessage());
        }

        return ResponseEntity.status(400).build();
    }

    @PreAuthorize("hasAuthority('review_menagement_write')")
    @PostMapping(value="/reviews/deny")
    public ResponseEntity<?> denyReview(@RequestBody @Min(1) @Max(100000)Integer id){

        List<CarReview> crList = carReviewService.findAll();
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try {

            for(CarReview cr : crList){
                if(cr.getId() == id){
                    cr.setDeleted(true);
                    carReviewService.save(cr);

                    String text = "Dear sir/madam, " + '\n';
                    text += "Your car review for" + cr.getCar().getName() + "has been denied by our administrator staff. \n ";
                    text += "\n\n\n" + "Sincerely, Rent a car support team.";
                    mailService.sendEmail(cr.getReviewer().getEmail(), text, "Car review has been denied");

                }
            }

            LOGGER.info("action=deny car review, user={}, result=success", user.getEmail());
            return ResponseEntity.ok().build();

        } catch (Exception e) {
            LOGGER.error("action=deny car review, user={}, result=failure, cause={}", user.getEmail(), e.getMessage());
        }

        return ResponseEntity.status(400).build();
    }


    @PreAuthorize("hasAuthority('user_menagement_write')")
    @PostMapping(value="/activateAcc")
    public ResponseEntity<?> activateAcc(@RequestBody String email){

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        UserRequest us = userRequestService.findByEmail(email);
        User u = new User();
        u.setFirstname(us.getFirstname());
        u.setLastname(us.getLastname());
        u.setEmail(us.getEmail());
        u.setPassword(us.getPassword());
        u.setBlocked(false);
        u.setDeleted(false);

        if (us.isCompany()) {

            Company com = new Company();
            com.setName(us.getName());
            com.setAddress(us.getAddress());
            com.setBussinessNumber(us.getPib());
            com.setDeleted(false);
            com.setOwner(u);
            companyService.save(com);
            u.setCompany(com);
            List<Role> rList = roleService.findByName("agent");
            u.setRole(rList);

        } else {

            u.setCompany(null);
            List<Role> rList = roleService.findByName("user");
            u.setRole(rList);

        }

        String text = "Dear sir/madam, " + '\n';
        text += "your account request has been reviewed and accepted by our administrator staff. \n Please follow the link below to activate your account.";
        text += uri + "/activateAcc/" + u.getEmail() + "\n\n\n" + "Sincerely, Rent a car support team.";

        try {

            mailService.sendEmail(u.getEmail(), "Account registration", text);
            userService.save(u);
            userRequestService.delete(us);

            LOGGER.info("action=activate account, user={}, result=success", user.getEmail());
            return ResponseEntity.ok().build();

        } catch (MessagingException | IOException | javax.mail.MessagingException e) {

            LOGGER.error("action=activate account, user={}, result=failure, cause={}", user.getEmail(), e.getMessage());

        }

        return ResponseEntity.status(400).build();

    }



    @RequestMapping(method = RequestMethod.GET, path = "/activateAcc/{mail:.+}")
    public ResponseEntity activateAcc(@PathVariable("mail") String mail, @Value("${front_uri}") String uri) throws URISyntaxException {

        try {

            User user = userService.findByEmail(mail);
            user.setActivated(true);
            userService.save(user);

            LOGGER.info("action=activate account, user={}, result=success", user.getEmail());

        } catch (Exception e) {
            LOGGER.error("action=activate account, user={}, result=failure, cause={}", mail, e.getMessage());
        }


        URI loginURI = new URI(uri + "/activateAcc");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(loginURI);

        return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
    }


    @PreAuthorize("hasAuthority('user_menagement_write')")
    @PostMapping(value="/ractivateAcc")
    public ResponseEntity<?> ractivateAcc(@RequestBody @NotBlank String email){

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try {

            User u = userService.findByEmail(email);
            u.setBlocked(false);
            userService.save(u);

            LOGGER.info("action=reactivate account, user={}, result=success", user.getEmail());
            return ResponseEntity.ok().build();

        } catch (Exception e) {
            LOGGER.error("action=reactivate account, user={}, result=failure, cause={}", user.getEmail(), e.getMessage());
        }

        return ResponseEntity.status(400).build();
    }


    @PreAuthorize("hasAuthority('user_menagement_write')")
    @PostMapping(value="/blockAcc")
    public ResponseEntity<?> blocAcc(@RequestBody @NotBlank String email){

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try {

            User u = userService.findByEmail(email);
            u.setBlocked(true);
            userService.save(u);

            LOGGER.info("action=block account, user={}, result=success", user.getEmail());
            return ResponseEntity.ok().build();

        } catch (Exception e) {
            LOGGER.error("action=block account, user={}, result=failure, cause={}", user.getEmail(), e.getMessage());
        }

        return ResponseEntity.status(400).build();

    }


    @PreAuthorize("hasAuthority('user_menagement_delete')")
    @PostMapping(value="/deleteAcc")
    public ResponseEntity<?> deleteAcc(@RequestBody@NotBlank String email){

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try {

            User u = userService.findByEmail(email);
            u.setDeleted(true);
            userService.save(u);

            String text = "Dear sir/madam, " + '\n';
            text += "Your account has been deleted pernamently by our administrator staff. \n This action cannot be undone.";
            text += "\n\n\n" + "Sincerely, Rent a car support team.";
            mailService.sendEmail(email, text, "Your account has been deleted pernamently");

            LOGGER.info("action=delete account, user={}, result=success", user.getEmail());
            return ResponseEntity.ok().build();

        } catch (Exception e) {
            LOGGER.error("action=delete account, user={}, result=failure, cause={}", user.getEmail(), e.getMessage());
        }

        return ResponseEntity.status(400).build();

    }


    @PreAuthorize("hasAuthority('codebook_menagement_write')")
    @PostMapping(value="/addCarC")
    public ResponseEntity<?> addCarClass(@RequestBody @NotBlank String name){

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try {

            CarClass cc = new CarClass();
            cc.setName(name);
            cc.setDeleted(false);
            carClassService.save(cc);

            LOGGER.info("action=add car class, user={}, result=success", user.getEmail());
            return ResponseEntity.ok().build();

        } catch (Exception e) {
            LOGGER.error("action=add car class, user={}, result=failure, cause={}", user.getEmail(), e.getMessage());
        }

        return ResponseEntity.status(400).build();

    }


    @PreAuthorize("hasAuthority('codebook_menagement_write')")
    @PostMapping(value="/addCarModel")
    public ResponseEntity<?> addCarModels(@RequestBody CarModelsDTO dto){

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try {

            CarModels cm = new CarModels();
            cm.setName(dto.getName());
            Manufacturer m = manufacturerService.findByName(dto.getManufacturer());
            cm.setManufacturer(m);
            CarClass cc = carClassService.findByName(dto.getCarClass());
            cm.setCarClass(cc);
            TransmissionType tt = transmissionTypeService.findByName(dto.getTransmission());
            cm.setTransmission(tt);
            cm.setDeleted(false);
            carModelsService.save(cm);

            LOGGER.info("action=add car model, user={}, result=success", user.getEmail());
            return ResponseEntity.ok().build();

        } catch (Exception e) {
            LOGGER.error("action=add car model, user={}, result=failure, cause={}", user.getEmail(), e.getMessage());
        }

        return ResponseEntity.status(400).build();

    }


    @PreAuthorize("hasAuthority('codebook_menagement_write')")
    @PostMapping(value="/addFuelType")
    public ResponseEntity<?> addFuelType(@RequestBody String name){

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try {

            FuelType cc = new FuelType();
            cc.setName(name);
            cc.setDeleted(false);
            fuelTypeService.save(cc);

            LOGGER.info("action=add fuel type, user={}, result=success", user.getEmail());
            return ResponseEntity.ok().build();

        } catch (Exception e) {
            LOGGER.error("action=add fuel type, user={}, result=failure, cause={}", user.getEmail(), e.getMessage());
        }

        return ResponseEntity.status(400).build();

    }


    @PreAuthorize("hasAuthority('codebook_menagement_write')")
    @PostMapping(value="/addManufac")
    public ResponseEntity<?> addManufacturer(@RequestBody @NotBlank String name){

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try {

            Manufacturer m = new Manufacturer();
            m.setName(name);
            m.setDeleted(false);
            manufacturerService.save(m);

            LOGGER.info("action=add manufacturer, user={}, result=success", user.getEmail());
            return ResponseEntity.ok().build();

        } catch (Exception e) {
            LOGGER.error("action=add manufacturer, user={}, result=failure, cause={}", user.getEmail(), e.getMessage());
        }

        return ResponseEntity.status(400).build();

    }


    @PreAuthorize("hasAuthority('codebook_menagement_write')")
    @PostMapping(value="/addTrans")
    public ResponseEntity<?> addTransmissionType(@RequestBody @NotBlank String name){

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try {

            TransmissionType tt = new TransmissionType();
            tt.setName(name);
            tt.setDeleted(false);
            transmissionTypeService.save(tt);

            LOGGER.info("action=add transmission type, user={}, result=success", user.getEmail());
            return ResponseEntity.ok().build();

        } catch (Exception e) {
            LOGGER.error("action=add transmission type, user={}, result=failure, cause={}", user.getEmail(), e.getMessage());
        }

        return ResponseEntity.status(400).build();

    }


    @PreAuthorize("hasAuthority('codebook_menagement_read')")
    @GetMapping(value="/getManufacturers")
    public List<ManufacturerDTO> getAllManufacturers(){

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try {

            List<Manufacturer> man = manufacturerService.findAll();

            List<ManufacturerDTO> dto = new ArrayList<>();

            for(Manufacturer m : man){
                ManufacturerDTO d = new ManufacturerDTO();
                d.setName(m.getName());
                d.setDeleted(m.isDeleted());
                dto.add(d);
            }

            LOGGER.info("action=get manufacturers, user={}, result=success", user.getEmail());
            return dto;

        } catch (Exception e) {
            LOGGER.error("action=get manufacturers, user={}, result=failure, cause={}", user.getEmail(), e.getMessage());
        }

        return null;
    }


    @PreAuthorize("hasAuthority('codebook_menagement_read')")
    @GetMapping(value="/getCarModels")
    public List<CarModelsDTO> getCarModels(){

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try {

            List<CarModels> cm = carModelsService.findAll();
            List<CarModelsDTO> dto = new ArrayList<>();

            for(CarModels c : cm){
                CarModelsDTO m = new CarModelsDTO();
                m.setName(c.getName());
                m.setCarClass(c.getCarClass().getName());
                m.setManufacturer(c.getManufacturer().getName());
                m.setTransmission(c.getTransmission().getName());
                m.setDeleted(c.isDeleted());
                dto.add(m);
            }

            LOGGER.info("action=get car models, user={}, result=success", user.getEmail());
            return dto;

        } catch (Exception e) {
            LOGGER.error("action=get car models, user={}, result=failure, cause={}", user.getEmail(), e.getMessage());
        }

        return null;
    }


    @PreAuthorize("hasAuthority('user_menagement_read')")
    @GetMapping(value="/getUserRequests")
    public List<UserRequestDTO> getUserRequest(){

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try {

            List<UserRequest> cm = userRequestService.findAll();
            List<UserRequestDTO> dto = new ArrayList<>();

            for(UserRequest c : cm){
                UserRequestDTO m = new UserRequestDTO();
                m.setFirsname(c.getFirstname());
                m.setLastname(c.getLastname());
                m.setEmail(c.getEmail());
                m.setPassword(c.getPassword());
                dto.add(m);
            }

            LOGGER.info("action=get user requests, user={}, result=success", user.getEmail());
            return dto;

        } catch (Exception e) {
            LOGGER.error("action=get user requests, user={}, result=failure, cause={}", user.getEmail(), e.getMessage());
        }

        return null;

    }


    @PreAuthorize("hasAuthority('user_menagement_read')")
    @GetMapping(value="/getUsers")
    public List<UserRequestDTO> getUsers(){

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try {

            List<User> cm = userService.findAll();
            List<UserRequestDTO> dto = new ArrayList<>();

            for(User c : cm){
                if(!c.isDeleted() && !c.isBlocked()) {
                    UserRequestDTO m = new UserRequestDTO();
                    m.setFirsname(c.getFirstname());
                    m.setLastname(c.getLastname());
                    m.setEmail(c.getEmail());
                    m.setPassword(c.getPassword());
                    dto.add(m);
                }
            }


            LOGGER.info("action=get users, user={}, result=success", user.getEmail());
            return dto;

        } catch (Exception e) {
            LOGGER.error("action=get users, user={}, result=failure, cause={}", user.getEmail(), e.getMessage());
        }

        return null;
    }


    @PreAuthorize("hasAuthority('user_menagement_read')")
    @GetMapping(value="/getBlockedUsers")
    public List<UserRequestDTO> getBlockedUsers(){

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try {

            List<User> cm = userService.findAll();
            List<UserRequestDTO> dto = new ArrayList<>();

            for(User c : cm){
                if(c.isBlocked()) {
                    UserRequestDTO m = new UserRequestDTO();
                    m.setFirsname(c.getFirstname());
                    m.setLastname(c.getLastname());
                    m.setEmail(c.getEmail());
                    m.setPassword(c.getPassword());
                    dto.add(m);
                }
            }

            LOGGER.info("action=get blocked users, user={}, result=success", user.getEmail());
            return dto;

        } catch (Exception e) {
            LOGGER.error("action=get blocked users, user={}, result=failure, cause={}", user.getEmail(), e.getMessage());
        }

        return null;
    }


    @PreAuthorize("hasAuthority('codebook_menagement_read')")
    @GetMapping(value="/getFuelTypes")
    public List<FuelTypeDTO> getAllFuelTypes(){

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try {

            List<FuelType> man = fuelTypeService.findAll();
            List<FuelTypeDTO> dto = new ArrayList<>();

            for(FuelType m : man){
                FuelTypeDTO d = new FuelTypeDTO();
                d.setName(m.getName());
                d.setDeleted(m.isDeleted());
                dto.add(d);
            }

            LOGGER.info("action=get fuel types, user={}, result=success", user.getEmail());
            return dto;

        } catch (Exception e) {
            LOGGER.error("action=get fuel types, user={}, result=failure, cause={}", user.getEmail(), e.getMessage());
        }

        return null;
    }


    @PreAuthorize("hasAuthority('codebook_menagement_read')")
    @GetMapping(value="/getCarClasses")
    public List<CarClassDTO> getAllCarClasses(){

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try {

            List<CarClass> man = carClassService.findAll();
            List<CarClassDTO> dto = new ArrayList<>();

            for(CarClass m : man){
                CarClassDTO d = new CarClassDTO();
                d.setName(m.getName());
                d.setDeleted(m.isDeleted());
                dto.add(d);
            }

            LOGGER.info("action=get car classes, user={}, result=success", user.getEmail());
            return dto;

        } catch (Exception e) {
            LOGGER.error("action=get car classes, user={}, result=failure, cause={}", user.getEmail(), e.getMessage());
        }

        return null;
    }


    @PreAuthorize("hasAuthority('codebook_menagement_read')")
    @GetMapping(value="/getTransmissions")
    public List<TransmissionDTO> getAllTransmissions(){

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try {

            List<TransmissionType> man = transmissionTypeService.findAll();
            List<TransmissionDTO> dto = new ArrayList<>();

            for(TransmissionType m : man){
                TransmissionDTO d = new TransmissionDTO();
                d.setName(m.getName());
                d.setDeleted(m.isDeleted());
                dto.add(d);
            }

            LOGGER.info("action=get transmission types, user={}, result=success", user.getEmail());
            return dto;

        } catch (Exception e) {
            LOGGER.error("action=get transmission types, user={}, result=failure, cause={}", user.getEmail(), e.getMessage());
        }

        return null;
    }


    @PreAuthorize("hasAuthority('codebook_menagement_delete')")
    @PostMapping(value="/deleteManufacturer")
    public ResponseEntity<?> deleteManufacturer(@RequestBody String name){

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try {

            Manufacturer man = manufacturerService.findByName(name);
            man.setDeleted(true);
            manufacturerService.save(man);

            LOGGER.info("action=delete manufacturer, user={}, result=success", user.getEmail());
            return ResponseEntity.ok().build();

        } catch (Exception e) {
            LOGGER.error("action=delete manufacturer, user={}, result=failure, cause={}", user.getEmail(), e.getMessage());
        }

        return ResponseEntity.status(400).build();

    }


    @PreAuthorize("hasAuthority('codebook_menagement_delete')")
    @PostMapping(value="/deleteCarModel")
    public ResponseEntity<?> deleteCarModel(@RequestBody String name){

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try {

            CarModels man = carModelsService.findByName(name);
            man.setDeleted(true);
            carModelsService.save(man);

            LOGGER.info("action=delete car model, user={}, result=success", user.getEmail());
            return ResponseEntity.ok().build();

        } catch (Exception e) {
            LOGGER.error("action=delete car model, user={}, result=failure, cause={}", user.getEmail(), e.getMessage());
        }

        return ResponseEntity.status(400).build();

    }


    @PreAuthorize("hasAuthority('codebook_menagement_delete')")
    @PostMapping(value="/deleteFuelType")
    public ResponseEntity<?> deleteFuelType(@RequestBody String name){

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try {

            FuelType man = fuelTypeService.findByName(name);
            man.setDeleted(true);
            fuelTypeService.save(man);

            LOGGER.info("action=delete fuel type, user={}, result=success", user.getEmail());
            return ResponseEntity.ok().build();

        } catch (Exception e) {
            LOGGER.error("action=delete fuel type, user={}, result=failure, cause={}", user.getEmail(), e.getMessage());
        }

        return ResponseEntity.status(400).build();
    }


    @PreAuthorize("hasAuthority('codebook_menagement_delete')")
    @PostMapping(value="/deleteTransmissionType")
    public ResponseEntity<?> deleteTransmissionType(@RequestBody String name){

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try {

            TransmissionType man = transmissionTypeService.findByName(name);
            man.setDeleted(true);
            transmissionTypeService.save(man);

            LOGGER.info("action=delete transmission type, user={}, result=success", user.getEmail());
            return ResponseEntity.ok().build();

        } catch (Exception e) {
            LOGGER.error("action=delete transmission type, user={}, result=failure, cause={}", user.getEmail(), e.getMessage());
        }

        return ResponseEntity.status(400).build();
    }


    @PreAuthorize("hasAuthority('codebook_menagement_delete')")
    @PostMapping(value="/deleteCarClass")
    public ResponseEntity<?> deleteCarClass(@RequestBody String name){

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try {

            CarClass man = carClassService.findByName(name);
            man.setDeleted(true);
            carClassService.save(man);

            LOGGER.info("action=delete car class, user={}, result=success", user.getEmail());
            return ResponseEntity.ok().build();

        } catch (Exception e) {
            LOGGER.error("action=delete car class, user={}, result=failure, cause={}", user.getEmail(), e.getMessage());
        }

        return ResponseEntity.status(400).build();
    }


    @PreAuthorize("hasAuthority('codebook_menagement_update')")
    @PostMapping(value="/updateManufacturer/{old}")
    public ResponseEntity<?> updateManufacturer(@PathVariable("old") String old, @RequestBody ManufacturerDTO dto){

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try {

            Manufacturer man = manufacturerService.findByName(old);
            man.setName(dto.getName());
            if(dto.isDeleted())
                man.setDeleted(false);

            manufacturerService.save(man);

            LOGGER.info("action=update manufacturer, user={}, result=success", user.getEmail());
            return ResponseEntity.ok().build();

        } catch (Exception e) {
            LOGGER.error("action=update manufacturer, user={}, result=failure, cause={}", user.getEmail(), e.getMessage());
        }

        return ResponseEntity.status(400).build();
    }


    @PreAuthorize("hasAuthority('codebook_menagement_update')")
    @PostMapping(value="/updateCarModel/{old}")
    public ResponseEntity<?> updateCarModel(@PathVariable("old") String old, @RequestBody CarModelsDTO dto){

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try {

            CarModels man = carModelsService.findByName(old);
            man.setName(dto.getName());
            if(dto.isDeleted())
                man.setDeleted(false);

            carModelsService.save(man);

            LOGGER.info("action=update car model, user={}, result=success", user.getEmail());
            return ResponseEntity.ok().build();

        } catch (Exception e) {
            LOGGER.error("action=update car model, user={}, result=failure, cause={}", user.getEmail(), e.getMessage());
        }

        return ResponseEntity.status(400).build();

    }


    @PreAuthorize("hasAuthority('codebook_menagement_update')")
    @PostMapping(value="/updateFuelType/{old}")
    public ResponseEntity<?> updateFuelType(@PathVariable("old") String old, @RequestBody FuelTypeDTO dto){

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try {

            FuelType man = fuelTypeService.findByName(old);
            man.setName(dto.getName());
            if(dto.isDeleted())
                man.setDeleted(false);

            fuelTypeService.save(man);

            LOGGER.info("action=update fuel type, user={}, result=success", user.getEmail());
            return ResponseEntity.ok().build();

        } catch (Exception e) {
            LOGGER.error("action=update fuel type, user={}, result=failure, cause={}", user.getEmail(), e.getMessage());
        }

        return ResponseEntity.status(400).build();

    }


    @PreAuthorize("hasAuthority('codebook_menagement_update')")
    @PostMapping(value="/updateCarClass/{old}")
    public ResponseEntity<?> updateCarClass(@PathVariable("old") String old, @RequestBody CarClassDTO dto){

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try {

            CarClass man = carClassService.findByName(old);
            man.setName(dto.getName());
            if(dto.isDeleted())
                man.setDeleted(false);

            carClassService.save(man);

            LOGGER.info("action=update car class, user={}, result=success", user.getEmail());
            return ResponseEntity.ok().build();

        } catch (Exception e) {
            LOGGER.error("action=update car class, user={}, result=failure, cause={}", user.getEmail(), e.getMessage());
        }

        return ResponseEntity.status(400).build();
    }


    @PreAuthorize("hasAuthority('codebook_menagement_update')")
    @PostMapping(value="/updateTransmissionType/{old}")
    public ResponseEntity<?> updateTransmissionType(@PathVariable("old") String old, @RequestBody TransmissionDTO dto){

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try {

            TransmissionType man = transmissionTypeService.findByName(old);
            man.setName(dto.getName());
            if(dto.isDeleted())
                man.setDeleted(false);

            transmissionTypeService.save(man);

            LOGGER.info("action=update transmission type, user={}, result=success", user.getEmail());
            return ResponseEntity.ok().build();

        } catch (Exception e) {
            LOGGER.error("action=update transmission type, user={}, result=failure, cause={}", user.getEmail(), e.getMessage());
        }

        return ResponseEntity.status(400).build();
    }


}
