package com.tpcindia.professional_couriers.controller;

import com.tpcindia.professional_couriers.service.DestinationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/destination")
public class DestinationController {

    @Autowired
    private DestinationService service;

    @GetMapping("/cities")
    public List<String> getCitiesByPinCode(@RequestParam String pinCode) {
        return service.getCitiesByPinCode(pinCode);
    }

}
