package com.tpcindia.professional_couriers.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tpcindia.professional_couriers.dto.CompanyMasterDTO;
import com.tpcindia.professional_couriers.service.CompanyMasterService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/companyMaster")
public class CompanyMasterController {
    
    @Autowired
    CompanyMasterService companyMasterService;

    @PostMapping("/addressDetails")
    public ResponseEntity<?> getAddressDetails(@RequestBody CompanyMasterDTO companyMasterDTO) {
        try {
            return companyMasterService.getAddressDetails(companyMasterDTO.getMasterCompanyCode());
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    
}
