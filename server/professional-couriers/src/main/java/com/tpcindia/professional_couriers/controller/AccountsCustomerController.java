package com.tpcindia.professional_couriers.controller;

import com.tpcindia.professional_couriers.dto.BranchDTO;
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
        return accountsCustomerService.getFirmNamesByBranch(firmNameDTO.getBranch());
    }

    @PostMapping("/branch")
    public ResponseEntity<String> getBranch(@RequestBody BranchDTO branchDTO) {
        String branch =  accountsCustomerService.findBranchByBranchCode(branchDTO.getBranchCode());
        if (branch != null) {
            return ResponseEntity.ok(branch);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Branch not found");
        }
    }

    @PostMapping("/customerEmail")
    public ResponseEntity<String> getCustomerEmailId(@RequestBody CustomerEmailDTO emailDTO) {
        String emailId = accountsCustomerService.findEmailByBranch(emailDTO.getCustCode());
        if (emailId != null) {
            return ResponseEntity.ok(emailId);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Branch email not found");
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
