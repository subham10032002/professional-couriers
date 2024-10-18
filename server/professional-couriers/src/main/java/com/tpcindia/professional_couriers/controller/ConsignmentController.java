package com.tpcindia.professional_couriers.controller;

import com.tpcindia.professional_couriers.dto.ConsignmentRequestDTO;
import com.tpcindia.professional_couriers.service.ConsignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class ConsignmentController {

    @Autowired
    private ConsignmentService consignmentService;

    @PostMapping("/consignment")
    public ResponseEntity<?> getConsignmentNumber(@RequestBody ConsignmentRequestDTO requestDTO) {
        Map<String, Object> result = null;
        try {
            result = consignmentService.getNextConsignmentNumber(requestDTO.getBranch(), requestDTO.getCustCode());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ofNullable(result);
    }
}
