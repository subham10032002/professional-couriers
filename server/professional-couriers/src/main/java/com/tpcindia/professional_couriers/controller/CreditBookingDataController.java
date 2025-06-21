package com.tpcindia.professional_couriers.controller;

import com.tpcindia.professional_couriers.dto.CreditBookingDataDTO;
import com.tpcindia.professional_couriers.dto.PdfDTO;
import com.tpcindia.professional_couriers.service.CreditBookingDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/credit-booking-data")
public class CreditBookingDataController {

    @Autowired
    private CreditBookingDataService creditBookingDataService;

    @PostMapping("/save")
    public ResponseEntity<?> submitCreditBooking(@RequestBody CreditBookingDataDTO creditBookingDataDTO) {
        try {
            return creditBookingDataService.saveCreditBookingData(creditBookingDataDTO);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to submit credit booking data: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/pdfDetails")
    public ResponseEntity<?> getTopPdfDetails(@RequestBody PdfDTO pdfDTO) {
        try {
            return creditBookingDataService.getCreditBookingData(pdfDTO.getBranch(), pdfDTO.getUsercode());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
