package com.ad_service.controller;

import java.util.ArrayList;
import java.util.List;

import com.ad_service.model.UserRequest;
import com.ad_service.service.UserRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/user_request/")
public class UserRequestController {

    @Autowired
    private UserRequestService userRequestService;

    @GetMapping(value = "user_request")
    public ResponseEntity<List<UserRequest>> getAll () {
        System.out.println("Pogodjen je UserRequestService");
        return new ResponseEntity((ArrayList<UserRequest>)userRequestService.findAll(), HttpStatus.OK);
    }

}
