package com.renting_service.controller;

import com.renting_service.dto.CarsDetailsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/gateway/")
public class GatewayController {

    @Autowired
    RentingController rentingController;

    @GetMapping(value = "getOne/{id}")
    public ResponseEntity<CarsDetailsDTO> getOneCar (@PathVariable("id") Integer id) {
        return rentingController.getOneCar(id);
    }



}
