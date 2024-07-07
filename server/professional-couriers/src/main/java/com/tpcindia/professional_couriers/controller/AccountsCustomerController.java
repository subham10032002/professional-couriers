package com.tpcindia.professional_couriers.controller;

import com.tpcindia.professional_couriers.dto.BranchEmailDTO;
import com.tpcindia.professional_couriers.dto.CustomerEmailDTO;
import com.tpcindia.professional_couriers.dto.FirmNameDTO;
import com.tpcindia.professional_couriers.service.AccountsCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/accountsCustomer")
public class AccountsCustomerController {

    @Autowired
    private AccountsCustomerService accountsCustomerService;

    @PostMapping("/firmNames")
    public List<String> getFirmNames(@RequestBody FirmNameDTO firmNameDTO) {
        return accountsCustomerService.getFirmNamesByBranch(firmNameDTO.getBranchCode());
    }

    @PostMapping("/customerEmail")
    public ResponseEntity<String> getCustomerEmailId(@RequestBody CustomerEmailDTO emailDTO) {
        String emailId = accountsCustomerService.findEmailByFirmName(emailDTO.getFirmName());
        if (emailId != null) {
            return ResponseEntity.ok(emailId);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email not found");
        }
    }

    @PostMapping("/branchEmail")
    public ResponseEntity<String> getBranchEmailId(@RequestBody BranchEmailDTO emailDTO) {
        String emailId = accountsCustomerService.findEmailByBranchCode(emailDTO.getBranchCode());
        if (emailId != null) {
            return ResponseEntity.ok(emailId);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Branch Email not found");
        }
    }
}
