package com.tpcindia.professional_couriers.controller;

import com.tpcindia.professional_couriers.dto.ConsignmentRequestDTO;
import com.tpcindia.professional_couriers.service.ConsignmentService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<Map<String, Object>> getConsignmentNumber(@RequestBody ConsignmentRequestDTO requestDTO) {
        Map<String, Object> result = null;
        try {
            result = consignmentService.getNextConsignmentNumber(requestDTO.getBranch());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ofNullable(result);
    }
}
