package com.tpcindia.professional_couriers.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.tpcindia.professional_couriers.model.CompanyMaster;
import com.tpcindia.professional_couriers.repository.CompanyMasterRepository;

@Service
public class CompanyMasterService {

    @Autowired
    private CompanyMasterRepository companyMasterRepository;


    public ResponseEntity<?> getAddressDetails(String masterCompanyCode) {
        CompanyMaster masterAddressDetails = companyMasterRepository.findAddressDetailsByCode(masterCompanyCode);

        if (masterAddressDetails == null) {
            return new ResponseEntity<>("No master details found for the given code: " + masterCompanyCode, HttpStatus.NOT_FOUND);
        }

        masterAddressDetails.setAddress(masterAddressDetails.getAddress().trim());
        masterAddressDetails.setContactNo(masterAddressDetails.getContactNo().trim());
        masterAddressDetails.setGstNo(masterAddressDetails.getGstNo().trim());

        return ResponseEntity.ofNullable(masterAddressDetails);
    }
    
}
