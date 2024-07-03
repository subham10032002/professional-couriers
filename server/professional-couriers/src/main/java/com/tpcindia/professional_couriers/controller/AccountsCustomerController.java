package com.tpcindia.professional_couriers.controller;

import com.tpcindia.professional_couriers.dto.FirmNameDTO;
import com.tpcindia.professional_couriers.service.AccountsCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
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
}
