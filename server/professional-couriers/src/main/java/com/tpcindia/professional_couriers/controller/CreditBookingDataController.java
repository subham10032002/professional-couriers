package com.tpcindia.professional_couriers.controller;

import com.tpcindia.professional_couriers.dto.AuthenticationRequestDTO;
import com.tpcindia.professional_couriers.dto.CBDataFetchDTO;
import com.tpcindia.professional_couriers.dto.CreditBookingDataDTO;
import com.tpcindia.professional_couriers.model.CreditBookingData;
import com.tpcindia.professional_couriers.model.UserLogin;
import com.tpcindia.professional_couriers.service.CreditBookingDataService;
import com.tpcindia.professional_couriers.utils.exceptions.InvalidCredentialsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/credit-booking-data")
public class CreditBookingDataController {

    @Autowired
    private CreditBookingDataService creditBookingDataService;

    @PostMapping("/save")
    public ResponseEntity<?> submitCreditBooking(@RequestBody CreditBookingDataDTO creditBookingDataDTO) {
        try {
            boolean isSubmitted = creditBookingDataService.saveCreditBookingData(creditBookingDataDTO);
            if (isSubmitted) {
                return new ResponseEntity<>("Credit booking data submitted successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Credit booking data not submitted", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to submit credit booking data: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/fetch")
    public ResponseEntity<?> fetchCreditBookingData(@RequestBody CBDataFetchDTO cbDataFetchDTO) {
        try {
            List<CreditBookingData> data = creditBookingDataService.getCreditBookingData(cbDataFetchDTO.getBranch());
            return new ResponseEntity<>(data, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to fetch credit booking data: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
