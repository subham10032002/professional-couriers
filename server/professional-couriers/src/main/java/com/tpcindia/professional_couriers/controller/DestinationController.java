package com.tpcindia.professional_couriers.controller;

import com.tpcindia.professional_couriers.service.DestinationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/destination")
public class DestinationController {

    @Autowired
    private DestinationService service;

    @GetMapping("/cities")
    public ResponseEntity<?> getDestinationsByPinCode(@RequestParam String pinCode) {
        try {
            return service.getDestinationsByPinCode(pinCode);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
