package com.admin_service.controller;



import com.admin_service.service.UserRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
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


    @GetMapping(value="/admin/carReviews")
    public List<CarReviewDTO> getCarReviews(){

        List<CarReview> cr = carReviewService.findAll();
        List<CarReviewDTO> dto = new ArrayList<>();
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

        return dto;

    }

    @PostMapping(value="/admin/carReviews")
    public ResponseEntity<?> approveReview(@RequestBody Integer id){

        List<CarReview> crList = carReviewService.findAll();

        for(CarReview cr : crList){
            if(cr.getId() == id){
                cr.setApproved(true);
                carReviewService.save(cr);
                Cars car = carService.getCar(cr.getCar().getId());
                car.getReviews().add(cr);
                carService.save(car);
            }
        }
        return ResponseEntity.ok().build();
    }

    //-----------------------------------------------------------------
    @PostMapping(value="/admin/activateAcc")
    public ResponseEntity<?> activateAcc(@RequestBody String email){
        UserRequest us = userRequestService.findByEmail(email);
        User u = new User();
        u.setFirstname(us.getFirstname());
        u.setLastname(us.getLastname());
        u.setEmail(us.getEmail());
        u.setPassword(us.getPassword());
        u.setBlocked(false);
        u.setDeleted(false);
        if(us.isCompany()){
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
        }else{
            u.setCompany(null);
            List<Role> rList = roleService.findByName("user");
            u.setRole(rList);
        }


        userService.save(u);
        userRequestService.delete(us);

        return ResponseEntity.ok().build();
    }

    @PostMapping(value="/admin/ractivateAcc")
    public ResponseEntity<?> ractivateAcc(@RequestBody String email){
        User u = userService.findByEmail(email);
        u.setBlocked(false);
        userService.save(u);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value="/admin/blockAcc")
    public ResponseEntity<?> blocAcc(@RequestBody String email){
        User u = userService.findByEmail(email);
        u.setBlocked(true);
        userService.save(u);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value="/admin/deleteAcc")
    public ResponseEntity<?> deleteAcc(@RequestBody String email){
        User u = userService.findByEmail(email);
        u.setDeleted(true);
        userService.save(u);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value="/admin/addCarC")
    public ResponseEntity<?> addCarClass(@RequestBody String name){
        try{
            CarClass cc = new CarClass();
            cc.setName(name);
            cc.setDeleted(false);
            carClassService.save(cc);
            return ResponseEntity.ok().build();
        }catch (Exception e){

        }
        return ResponseEntity.status(400).build();
    }

    @PostMapping(value="/admin/addCarModel")
    public ResponseEntity<?> addCarModels(@RequestBody CarModelsDTO dto){
        try{
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
            return ResponseEntity.ok().build();
        }catch (Exception e){

        }
        return ResponseEntity.status(400).build();
    }

    @PostMapping(value="/admin/addFuelType")
    public ResponseEntity<?> addFuelType(@RequestBody String name){
        try{
            FuelType cc = new FuelType();
            cc.setName(name);
            cc.setDeleted(false);
            fuelTypeService.save(cc);
            return ResponseEntity.ok().build();
        }catch (Exception e){

        }
        return ResponseEntity.ok().build();
        //return ResponseEntity.status(400).build();
    }

    @PostMapping(value="/admin/addManufac")
    public ResponseEntity<?> addManufacturer(@RequestBody String name){
        try{
            Manufacturer m = new Manufacturer();
            m.setName(name);
            m.setDeleted(false);
            manufacturerService.save(m);
            return ResponseEntity.ok().build();
        }catch (Exception e){

        }
        return ResponseEntity.status(400).build();
    }

    @PostMapping(value="/admin/addTrans")
    public ResponseEntity<?> addTransmissionType(@RequestBody String name){
        try{
            TransmissionType tt = new TransmissionType();
            tt.setName(name);
            tt.setDeleted(false);
            transmissionTypeService.save(tt);
            return ResponseEntity.ok().build();
        }catch (Exception e){

        }
        return ResponseEntity.status(400).build();
    }

    @GetMapping(value="/admin/getManufacturers")
    public List<ManufacturerDTO> getAllManufacturers(){

        List<Manufacturer> man = manufacturerService.findAll();

        List<ManufacturerDTO> dto = new ArrayList<>();

        for(Manufacturer m : man){
            ManufacturerDTO d = new ManufacturerDTO();
            d.setName(m.getName());
            d.setDeleted(m.isDeleted());
            dto.add(d);
        }

        return dto;
    }

    @GetMapping(value="/admin/getCarModels")
    public List<CarModelsDTO> getCarModels(){

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

        return dto;
    }

    @GetMapping(value="/admin/getUserRequests")
    public List<UserRequestDTO> getUserRequest(){

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

        return dto;
    }

    @GetMapping(value="/admin/getUsers")
    public List<UserRequestDTO> getUsers(){

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

        return dto;
    }

    @GetMapping(value="/admin/getBlockedUsers")
    public List<UserRequestDTO> getBlockedUsers(){

        List<User> cm = userService.findAll();

        List<UserRequestDTO> dto = new ArrayList<>();

        for(User c : cm){
            if(c.isBlocked()==true) {
                UserRequestDTO m = new UserRequestDTO();
                m.setFirsname(c.getFirstname());
                m.setLastname(c.getLastname());
                m.setEmail(c.getEmail());
                m.setPassword(c.getPassword());
                dto.add(m);
            }
        }

        return dto;
    }

    @GetMapping(value="/admin/getFuelTypes")
    public List<FuelTypeDTO> getAllFuelTypes(){

        List<FuelType> man = fuelTypeService.findAll();

        List<FuelTypeDTO> dto = new ArrayList<>();

        for(FuelType m : man){
            FuelTypeDTO d = new FuelTypeDTO();
            d.setName(m.getName());
            d.setDeleted(m.isDeleted());
            dto.add(d);
        }

        return dto;
    }

    @GetMapping(value="/admin/getCarClasses")
    public List<CarClassDTO> getAllCarClasses(){

        List<CarClass> man = carClassService.findAll();

        List<CarClassDTO> dto = new ArrayList<>();

        for(CarClass m : man){
            CarClassDTO d = new CarClassDTO();
            d.setName(m.getName());
            d.setDeleted(m.isDeleted());
            dto.add(d);
        }

        return dto;
    }

    @GetMapping(value="/admin/getTransmissions")
    public List<TransmissionDTO> getAllTransmissions(){

        List<TransmissionType> man = transmissionTypeService.findAll();

        List<TransmissionDTO> dto = new ArrayList<>();

        for(TransmissionType m : man){
            TransmissionDTO d = new TransmissionDTO();
            d.setName(m.getName());
            d.setDeleted(m.isDeleted());
            dto.add(d);
        }

        return dto;
    }

    @PostMapping(value="/admin/deleteManufacturer")
    public ResponseEntity<?> deleteManufacturer(@RequestBody String name){

        Manufacturer man = manufacturerService.findByName(name);
        man.setDeleted(true);
        manufacturerService.save(man);

        return ResponseEntity.ok().build();
    }

    @PostMapping(value="/admin/deleteCarModel")
    public ResponseEntity<?> deleteCarModel(@RequestBody String name){

        CarModels man = carModelsService.findByName(name);
        man.setDeleted(true);
        carModelsService.save(man);

        return ResponseEntity.ok().build();
    }

    @PostMapping(value="/admin/deleteFuelType")
    public ResponseEntity<?> deleteFuelType(@RequestBody String name){

        FuelType man = fuelTypeService.findByName(name);
        man.setDeleted(true);
        fuelTypeService.save(man);

        return ResponseEntity.ok().build();
    }

    @PostMapping(value="/admin/deleteTransmissionType")
    public ResponseEntity<?> deleteTransmissionType(@RequestBody String name){

        TransmissionType man = transmissionTypeService.findByName(name);
        man.setDeleted(true);
        transmissionTypeService.save(man);

        return ResponseEntity.ok().build();
    }

    @PostMapping(value="/admin/deleteCarClass")
    public ResponseEntity<?> deleteCarClass(@RequestBody String name){

        CarClass man = carClassService.findByName(name);
        man.setDeleted(true);
        carClassService.save(man);

        return ResponseEntity.ok().build();
    }

    @PostMapping(value="/admin/updateManufacturer/{old}")
    public ResponseEntity<?> updateManufacturer(@PathVariable("old") String old, @RequestBody ManufacturerDTO dto){

        Manufacturer man = manufacturerService.findByName(old);
        man.setName(dto.getName());
        if(dto.isDeleted())
            man.setDeleted(false);

        manufacturerService.save(man);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value="/admin/updateCarModel/{old}")
    public ResponseEntity<?> updateCarModel(@PathVariable("old") String old, @RequestBody CarModelsDTO dto){

        CarModels man = carModelsService.findByName(old);
        man.setName(dto.getName());
        if(dto.isDeleted())
            man.setDeleted(false);

        carModelsService.save(man);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value="/admin/updateFuelType/{old}")
    public ResponseEntity<?> updateFuelType(@PathVariable("old") String old, @RequestBody FuelTypeDTO dto){

        FuelType man = fuelTypeService.findByName(old);
        man.setName(dto.getName());
        if(dto.isDeleted())
            man.setDeleted(false);

        fuelTypeService.save(man);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value="/admin/updateCarClass/{old}")
    public ResponseEntity<?> updateCarClass(@PathVariable("old") String old, @RequestBody CarClassDTO dto){

        CarClass man = carClassService.findByName(old);
        man.setName(dto.getName());
        if(dto.isDeleted())
            man.setDeleted(false);


        carClassService.save(man);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value="/admin/updateTransmissionType/{old}")
    public ResponseEntity<?> updateTransmissionType(@PathVariable("old") String old, @RequestBody TransmissionDTO dto){

        TransmissionType man = transmissionTypeService.findByName(old);
        man.setName(dto.getName());
        if(dto.isDeleted())
            man.setDeleted(false);


        transmissionTypeService.save(man);
        return ResponseEntity.ok().build();
    }

}
