package com.rent_a_car.agentski_bekend.controller;
import com.rent_a_car.agentski_bekend.dto.*;
import com.rent_a_car.agentski_bekend.model.*;
import com.rent_a_car.agentski_bekend.service.CarsService;
import com.rent_a_car.agentski_bekend.service.interfaces.*;
import com.sun.xml.messaging.saaj.packaging.mime.MessagingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
//import org.springframework.ws.mime.MimeMessage;

import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@Validated
public class AdminController {

    @Autowired
    private CarClassServiceInterface carClassService;

    @Autowired
    private ManufacturerServiceInterface manufacturerService;

    @Autowired
    private TransmissionTypeServiceInterface transmissionTypeService;

    @Autowired
    private FuelTypeServiceInterface fuelTypeService;

    @Autowired
    private CarModelsServiceInterface carModelsService;

    @Autowired
    private UserRequestServiceInterface userRequestService;

    @Autowired
    private UserServiceInterface userService;

    @Autowired
    private CarReviewServiceInterface carReviewService;

    @Autowired
    private CompanyServiceInterface companyService;

    @Autowired
    private RoleServiceInterface roleService;

    @Autowired
    private CarsService carService;

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${back-uri}")
    private String uri;

    private static final Logger LOGGER = LogManager.getLogger(RentingController.class.getName());



    @PreAuthorize("hasAuthority('review_menagement_read')")
    @GetMapping(value="/admin/carReviews")
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

            LOGGER.info("Action get all car reviews by user: {} successful", user.getEmail());
            return dto;

        } catch (Exception e) {
            LOGGER.error("Action get all car reviews by user: {} failed. Cause: {}", user.getEmail(), e.getMessage());
        }

        return null;
    }

    @PreAuthorize("hasAuthority('review_menagement_write')")
    @PostMapping(value="/admin/carReviews")
    public ResponseEntity<?> approveReview(@RequestBody @Min(1) @Max(100000)Integer id){

        List<CarReview> crList = carReviewService.findAll();
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

       try {

           for(CarReview cr : crList){
               if(cr.getId() == id){
                   cr.setApproved(true);
                   carReviewService.save(cr);
                   Cars car = carService.getCar(cr.getCar().getId());
                   car.getReviews().add(cr);
                   carService.save(car);
               }
           }

           LOGGER.info("Action approve car review id:{} by user: {} successful", id.toString(), user.getEmail());
           return ResponseEntity.ok().build();

       } catch (Exception e) {
           LOGGER.error("Action approve car review id:{} by user: {} failed. Cause: {}", id.toString(), user.getEmail(), e.getMessage());
       }

       return ResponseEntity.status(400).build();
    }

//-----------------------------------------------------------------
    @PreAuthorize("hasAuthority('user_menagement_write')")
    @PostMapping(value="/admin/activateAcc")
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
            com.setBussinessNumber(us.getNumber());
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


        try {

            sendAcceptEmail(u.getEmail());
            userService.save(u);
            userRequestService.delete(us);

            LOGGER.info("Validation email has been sent to user: {} by administrator: {}", u.getEmail(), user.getEmail());
            return ResponseEntity.ok().build();

        } catch (MessagingException | IOException | javax.mail.MessagingException e) {

            LOGGER.error("Validation email has NOT been sent to user: {} by administrator: {}. Cause: {}", u.getEmail(), user.getEmail(), e.getMessage());

        }

        return ResponseEntity.status(400).build();

    }


    @RequestMapping(method = RequestMethod.GET, path = "/activateAcc/{mail:.+}")
    public ResponseEntity activateAcc(@PathVariable("mail") String mail, @Value("${front_uri}") String uri) throws URISyntaxException {

        User u = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try {

            User user = userService.findByEmail(mail);
            user.setActivated(true);
            userService.save(user);

            LOGGER.info("User: {} activated the account with email: {}", u.getEmail(), mail);

        } catch (Exception e) {
            LOGGER.error("User: {} failed to activate the account with email: {}. Cause: {}", u.getEmail(), mail, e.getMessage());
        }


        URI loginURI = new URI(uri + "/activateAcc");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(loginURI);

        return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
    }


    @PreAuthorize("hasAuthority('user_menagement_write')")
    @PostMapping(value="/admin/ractivateAcc")
    public ResponseEntity<?> ractivateAcc(@RequestBody @NotBlank String email){

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try {

            User u = userService.findByEmail(email);
            u.setBlocked(false);
            userService.save(u);

            LOGGER.info("User: {} reactivated the account email: {} successfully.", user.getEmail(), email);
            return ResponseEntity.ok().build();

        } catch (Exception e) {
            LOGGER.error("User: {} failed to reactivate the account email: {}. Cause: {}", user.getEmail(), email, e.getMessage());
        }

        return ResponseEntity.status(400).build();
    }


    @PreAuthorize("hasAuthority('user_menagement_write')")
    @PostMapping(value="/admin/blockAcc")
    public ResponseEntity<?> blocAcc(@RequestBody @NotBlank String email){

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try {

            User u = userService.findByEmail(email);
            u.setBlocked(true);
            userService.save(u);

            LOGGER.info("User: {} blocked the account email: {} successfully.", user.getEmail(), email);
            return ResponseEntity.ok().build();

        } catch (Exception e) {
            LOGGER.error("User: {} failed to block the account email: {}. Cause: {}", user.getEmail(), email, e.getMessage());
        }

        return ResponseEntity.status(400).build();

    }


    @PreAuthorize("hasAuthority('user_menagement_delete')")
    @PostMapping(value="/admin/deleteAcc")
    public ResponseEntity<?> deleteAcc(@RequestBody@NotBlank String email){

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try {

            User u = userService.findByEmail(email);
            u.setDeleted(true);
            userService.save(u);

            LOGGER.info("User: {} deleted the account email: {} successfully.", user.getEmail(), email);
            return ResponseEntity.ok().build();

        } catch (Exception e) {
            LOGGER.error("User: {} failed to delete the account email: {}. Cause: {}", user.getEmail(), email, e.getMessage());
        }

        return ResponseEntity.status(400).build();

    }


    @PreAuthorize("hasAuthority('codebook_menagement_write')")
    @PostMapping(value="/admin/addCarC")
    public ResponseEntity<?> addCarClass(@RequestBody @NotBlank String name){

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try {

            CarClass cc = new CarClass();
            cc.setName(name);
            cc.setDeleted(false);
            carClassService.save(cc);

            LOGGER.info("User: {} added new car class: {} successfully.", user.getEmail(), name);
            return ResponseEntity.ok().build();

        } catch (Exception e) {
            LOGGER.error("User: {} failed to add new class: {}. Cause: {}", user.getEmail(), name, e.getMessage());
        }

        return ResponseEntity.status(400).build();

    }


    @PreAuthorize("hasAuthority('codebook_menagement_write')")
    @PostMapping(value="/admin/addCarModel")
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

            LOGGER.info("User: {} added new car model: {} successfully.", user.getEmail(), dto.getName());
            return ResponseEntity.ok().build();

        } catch (Exception e) {
            LOGGER.error("User: {} failed to add new car model: {}. Cause: {}", user.getEmail(), dto.getName(), e.getMessage());
        }

        return ResponseEntity.status(400).build();

    }


    @PreAuthorize("hasAuthority('codebook_menagement_write')")
    @PostMapping(value="/admin/addFuelType")
    public ResponseEntity<?> addFuelType(@RequestBody String name){

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try {

            FuelType cc = new FuelType();
            cc.setName(name);
            cc.setDeleted(false);
            fuelTypeService.save(cc);

            LOGGER.info("User: {} added new fuel type: {} successfully.", user.getEmail(), name);
            return ResponseEntity.ok().build();

        } catch (Exception e) {
            LOGGER.error("User: {} failed to add new fuel type: {}. Cause: {}", user.getEmail(), name, e.getMessage());
        }

        return ResponseEntity.status(400).build();

    }


    @PreAuthorize("hasAuthority('codebook_menagement_write')")
    @PostMapping(value="/admin/addManufac")
    public ResponseEntity<?> addManufacturer(@RequestBody @NotBlank String name){

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try {

            Manufacturer m = new Manufacturer();
            m.setName(name);
            m.setDeleted(false);
            manufacturerService.save(m);

            LOGGER.info("User: {} added new nanufacturer: {} successfully.", user.getEmail(), name);
            return ResponseEntity.ok().build();

        } catch (Exception e) {
            LOGGER.error("User: {} failed to add new manufacturer: {}. Cause: {}", user.getEmail(), name, e.getMessage());
        }

        return ResponseEntity.status(400).build();

    }


    @PreAuthorize("hasAuthority('codebook_menagement_write')")
    @PostMapping(value="/admin/addTrans")
    public ResponseEntity<?> addTransmissionType(@RequestBody @NotBlank String name){

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try {

            TransmissionType tt = new TransmissionType();
            tt.setName(name);
            tt.setDeleted(false);
            transmissionTypeService.save(tt);

            LOGGER.info("User: {} added new transmission type: {} successfully.", user.getEmail(), name);
            return ResponseEntity.ok().build();

        } catch (Exception e) {
            LOGGER.error("User: {} failed to add new transmission type: {}. Cause: {}", user.getEmail(), name, e.getMessage());
        }

        return ResponseEntity.status(400).build();

    }


    @PreAuthorize("hasAuthority('codebook_menagement_read')")
    @GetMapping(value="/admin/getManufacturers")
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

            LOGGER.info("Action get all manufactures by user: {} successful", user.getEmail());
            return dto;

        } catch (Exception e) {
            LOGGER.error("Action get all manufactures by user: {} failed. Cause: {}", user.getEmail(), e.getMessage());
        }

        return null;
    }


    @PreAuthorize("hasAuthority('codebook_menagement_read')")
    @GetMapping(value="/admin/getCarModels")
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

            LOGGER.info("Action get all car models by user: {} successful", user.getEmail());
            return dto;

        } catch (Exception e) {
            LOGGER.error("Action get all car models by user: {} failed. Cause: {}", user.getEmail(), e.getMessage());
        }

        return null;
    }


    @PreAuthorize("hasAuthority('user_menagement_read')")
    @GetMapping(value="/admin/getUserRequests")
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

            LOGGER.info("Action get user requests by user: {} successful", user.getEmail());
            return dto;

        } catch (Exception e) {
            LOGGER.error("Action get user requests by user: {} failed. Cause: {}", user.getEmail(), e.getMessage());
        }

        return null;

    }


    @PreAuthorize("hasAuthority('user_menagement_read')")
    @GetMapping(value="/admin/getUsers")
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


            LOGGER.info("Action get users by user: {} successful", user.getEmail());
            return dto;

        } catch (Exception e) {
            LOGGER.error("Action get users by user: {} failed. Cause: {}", user.getEmail(), e.getMessage());
        }

        return null;
    }


    @PreAuthorize("hasAuthority('user_menagement_read')")
    @GetMapping(value="/admin/getBlockedUsers")
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

            LOGGER.info("Action get blocked users by user: {} successful", user.getEmail());
            return dto;

        } catch (Exception e) {
            LOGGER.error("Action get blocked users by user: {} failed. Cause: {}", user.getEmail(), e.getMessage());
        }

        return null;
    }


    @PreAuthorize("hasAuthority('codebook_menagement_read')")
    @GetMapping(value="/admin/getFuelTypes")
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

            LOGGER.info("Action get all fuel types by user: {} successful", user.getEmail());
            return dto;

        } catch (Exception e) {
            LOGGER.error("Action get all fuel types by user: {} failed. Cause: {}", user.getEmail(), e.getMessage());
        }

        return null;
    }


    @PreAuthorize("hasAuthority('codebook_menagement_read')")
    @GetMapping(value="/admin/getCarClasses")
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

            LOGGER.info("Action get all car classes by user: {} successful", user.getEmail());
            return dto;

        } catch (Exception e) {
            LOGGER.error("Action get all car classes by user: {} failed. Cause: {}", user.getEmail(), e.getMessage());
        }

        return null;
    }


    @PreAuthorize("hasAuthority('codebook_menagement_read')")
    @GetMapping(value="/admin/getTransmissions")
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

            LOGGER.info("Action get all transmission types by user: {} successful", user.getEmail());
            return dto;

        } catch (Exception e) {
            LOGGER.error("Action get all transmission types by user: {} failed. Cause: {}", user.getEmail(), e.getMessage());
        }

        return null;
    }


    @PreAuthorize("hasAuthority('codebook_menagement_delete')")
    @PostMapping(value="/admin/deleteManufacturer")
    public ResponseEntity<?> deleteManufacturer(@RequestBody String name){

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try {

            Manufacturer man = manufacturerService.findByName(name);
            man.setDeleted(true);
            manufacturerService.save(man);

            LOGGER.info("User: {} successfully deleted manufacturer: {}", user.getEmail(), name);
            return ResponseEntity.ok().build();

        } catch (Exception e) {
            LOGGER.error("User: {} failed to delete manufacturer: {}. Cause: {}", user.getEmail(), name, e.getMessage());
        }

        return ResponseEntity.status(400).build();

    }


    @PreAuthorize("hasAuthority('codebook_menagement_delete')")
    @PostMapping(value="/admin/deleteCarModel")
    public ResponseEntity<?> deleteCarModel(@RequestBody String name){

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try {

            CarModels man = carModelsService.findByName(name);
            man.setDeleted(true);
            carModelsService.save(man);

            LOGGER.info("User: {} successfully deleted car model: {}", user.getEmail(), name);
            return ResponseEntity.ok().build();

        } catch (Exception e) {
            LOGGER.error("User: {} failed to delete car model: {}. Cause: {}", user.getEmail(), name, e.getMessage());
        }

        return ResponseEntity.status(400).build();

    }


    @PreAuthorize("hasAuthority('codebook_menagement_delete')")
    @PostMapping(value="/admin/deleteFuelType")
    public ResponseEntity<?> deleteFuelType(@RequestBody String name){

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try {

            FuelType man = fuelTypeService.findByName(name);
            man.setDeleted(true);
            fuelTypeService.save(man);

            LOGGER.info("User: {} successfully deleted fuel type: {}", user.getEmail(), name);
            return ResponseEntity.ok().build();

        } catch (Exception e) {
            LOGGER.error("User: {} failed to delete fuel type: {}. Cause: {}", user.getEmail(), name, e.getMessage());
        }

        return ResponseEntity.status(400).build();
    }


    @PreAuthorize("hasAuthority('codebook_menagement_delete')")
    @PostMapping(value="/admin/deleteTransmissionType")
    public ResponseEntity<?> deleteTransmissionType(@RequestBody String name){

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try {

            TransmissionType man = transmissionTypeService.findByName(name);
            man.setDeleted(true);
            transmissionTypeService.save(man);

            LOGGER.info("User: {} successfully deleted transmission type: {}", user.getEmail(), name);
            return ResponseEntity.ok().build();

        } catch (Exception e) {
            LOGGER.error("User: {} failed to delete transmission type: {}. Cause: {}", user.getEmail(), name, e.getMessage());
        }

        return ResponseEntity.status(400).build();
    }


    @PreAuthorize("hasAuthority('codebook_menagement_delete')")
    @PostMapping(value="/admin/deleteCarClass")
    public ResponseEntity<?> deleteCarClass(@RequestBody String name){

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try {

            CarClass man = carClassService.findByName(name);
            man.setDeleted(true);
            carClassService.save(man);

            LOGGER.info("User: {} successfully deleted car class: {}", user.getEmail(), name);
            return ResponseEntity.ok().build();

        } catch (Exception e) {
            LOGGER.error("User: {} failed to delete car class: {}. Cause: {}", user.getEmail(), name, e.getMessage());
        }

        return ResponseEntity.status(400).build();
    }


    @PreAuthorize("hasAuthority('codebook_menagement_update')")
    @PostMapping(value="/admin/updateManufacturer/{old}")
    public ResponseEntity<?> updateManufacturer(@PathVariable("old") String old, @RequestBody ManufacturerDTO dto){

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try {

            Manufacturer man = manufacturerService.findByName(old);
            man.setName(dto.getName());
            if(dto.isDeleted())
                man.setDeleted(false);

            manufacturerService.save(man);

            LOGGER.info("User: {} updated manufacturer: {} successfully.", user.getEmail(), old);
            return ResponseEntity.ok().build();

        } catch (Exception e) {
            LOGGER.error("User: {} failed to update manufacturer: {}. Cause: {}", user.getEmail(), old, e.getMessage());
        }

        return ResponseEntity.status(400).build();
    }


    @PreAuthorize("hasAuthority('codebook_menagement_update')")
    @PostMapping(value="/admin/updateCarModel/{old}")
    public ResponseEntity<?> updateCarModel(@PathVariable("old") String old, @RequestBody CarModelsDTO dto){

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try {

            CarModels man = carModelsService.findByName(old);
            man.setName(dto.getName());
            if(dto.isDeleted())
                man.setDeleted(false);

            carModelsService.save(man);

            LOGGER.info("User: {} updated car model: {} successfully.", user.getEmail(), old);
            return ResponseEntity.ok().build();

        } catch (Exception e) {
            LOGGER.error("User: {} failed to update car model: {}. Cause: {}", user.getEmail(), old, e.getMessage());
        }

        return ResponseEntity.status(400).build();

    }


    @PreAuthorize("hasAuthority('codebook_menagement_update')")
    @PostMapping(value="/admin/updateFuelType/{old}")
    public ResponseEntity<?> updateFuelType(@PathVariable("old") String old, @RequestBody FuelTypeDTO dto){

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try {

            FuelType man = fuelTypeService.findByName(old);
            man.setName(dto.getName());
            if(dto.isDeleted())
                man.setDeleted(false);

            fuelTypeService.save(man);

            LOGGER.info("User: {} updated fuel type: {} successfully.", user.getEmail(), old);
            return ResponseEntity.ok().build();

        } catch (Exception e) {
            LOGGER.error("User: {} failed to update fuel type: {}. Cause: {}", user.getEmail(), old, e.getMessage());
        }

        return ResponseEntity.status(400).build();

    }


    @PreAuthorize("hasAuthority('codebook_menagement_update')")
    @PostMapping(value="/admin/updateCarClass/{old}")
    public ResponseEntity<?> updateCarClass(@PathVariable("old") String old, @RequestBody CarClassDTO dto){

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try {

            CarClass man = carClassService.findByName(old);
            man.setName(dto.getName());
            if(dto.isDeleted())
                man.setDeleted(false);

            carClassService.save(man);

            LOGGER.info("User: {} updated car class: {} successfully.", user.getEmail(), old);
            return ResponseEntity.ok().build();

        } catch (Exception e) {
            LOGGER.error("User: {} failed to update car class: {}. Cause: {}", user.getEmail(), old, e.getMessage());
        }

        return ResponseEntity.status(400).build();
    }


    @PreAuthorize("hasAuthority('codebook_menagement_update')")
    @PostMapping(value="/admin/updateTransmissionType/{old}")
    public ResponseEntity<?> updateTransmissionType(@PathVariable("old") String old, @RequestBody TransmissionDTO dto){

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try {

            TransmissionType man = transmissionTypeService.findByName(old);
            man.setName(dto.getName());
            if(dto.isDeleted())
                man.setDeleted(false);

            transmissionTypeService.save(man);

            LOGGER.info("User: {} updated transmission type: {} successfully.", user.getEmail(), old);
            return ResponseEntity.ok().build();

        } catch (Exception e) {
            LOGGER.error("User: {} failed to update transmission type: {}. Cause: {}", user.getEmail(), old, e.getMessage());
        }

        return ResponseEntity.status(400).build();
    }


    void sendAcceptEmail(String sendTo) throws MessagingException, IOException, javax.mail.MessagingException {

        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        helper.setTo(sendTo);

        helper.setSubject("Centro Clinico account registration");
        String text = "Dear sir/madam, " + '\n';
        text += "your account request has been reviewed and accepted by our administrator staff. \n Please follow the link below to activate your account.";
        text += uri + "/activateAcc/" + sendTo + "\n\n\n" + "Sincerely, Rent a car support team.";
//        text += "http://localhost:4200/activateAcc/" + sendTo + "\n\n\n" + "Sincerely, Rent a car support team.";

        helper.setText(text);

        try {
            javaMailSender.send(msg);
            LOGGER.info("Activation link for account: {} has been sent", sendTo);
        } catch (Exception e) {
            LOGGER.warn("Activation link for account: {} has NOT been sent. Cause: {}", sendTo, e.getMessage());
        }

    }

//    void sendDeclineEmail(String sendTo, String description, String firstName, String lastName) {
//
//        SimpleMailMessage msg = new SimpleMailMessage();
//        msg.setTo(sendTo);
//
//        msg.setSubject("Centro Clinico account registration");
//        String text = "Dear sir/madam, " + '\n';
//        text += "your account request has been reviewed. Unfortunately, it has been declined, with an administrator message attached:";
//        text += "\n\n\n" + "Sincerely, Centro Clinico support team.";
//        msg.setText(text);
//
//        javaMailSender.send(msg);
//
//    }
}
