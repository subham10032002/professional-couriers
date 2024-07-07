package com.tpcindia.professional_couriers.service;

import com.tpcindia.professional_couriers.model.AccountsCustomer;
import com.tpcindia.professional_couriers.repository.AccountsCustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.lang.System.in;

@Service
public class AccountsCustomerService {

    @Autowired
    private AccountsCustomerRepository accountsCustomerRepository;

    public List<String> getFirmNamesByBranch(String branchCode) {
        List<String> firmNames = accountsCustomerRepository.findFirmNamesByTypeAndBranch(branchCode);
        firmNames.replaceAll(String::trim);
        return firmNames;
    }

    public String findEmailByFirmName(String firmName) {
        String email = accountsCustomerRepository.findEmailByFirmNameAndType(firmName);
        if (email != null) {
            return email.trim();
        } else {
            return null;
        }
    }

    public String findEmailByBranchCode(String branchCode) {
        String email = accountsCustomerRepository.findEmailByBranchCodeAndType(branchCode);
        if (email != null) {
            return email.trim();
        } else {
            return null;
        }
    }
}
