package com.rent_a_car.agentski_bekend.controller;
import com.rent_a_car.agentski_bekend.dto.CarClassDTO;
import com.rent_a_car.agentski_bekend.dto.FuelTypeDTO;
import com.rent_a_car.agentski_bekend.dto.ManufacturerDTO;
import com.rent_a_car.agentski_bekend.dto.TransmissionDTO;
import com.rent_a_car.agentski_bekend.model.CarClass;
import com.rent_a_car.agentski_bekend.model.FuelType;
import com.rent_a_car.agentski_bekend.model.Manufacturer;
import com.rent_a_car.agentski_bekend.model.TransmissionType;
import com.rent_a_car.agentski_bekend.service.interfaces.CarClassServiceInterface;
import com.rent_a_car.agentski_bekend.service.interfaces.FuelTypeServiceInterface;
import com.rent_a_car.agentski_bekend.service.interfaces.ManufacturerServiceInterface;
import com.rent_a_car.agentski_bekend.service.interfaces.TransmissionTypeServiceInterface;
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
