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

    public List<String> getFirmNamesByBranch(String branch) {
        List<String> firmNames = accountsCustomerRepository.findFirmNamesByTypeAndBranch(branch);
        firmNames.replaceAll(String::trim);
        return firmNames;
    }

    public String findBranchByBranchCode(String branchCode) {
        String branch = accountsCustomerRepository.findBranchByBranchCode(branchCode);
        if (branch != null) {
            return branch.trim();
        } else {
            return null;
        }
    }

    public List<String> findEmailByBranch(String branch) {
        List<String> email = accountsCustomerRepository.findEmailByBranchAndType(branch);
        email.replaceAll(String::trim);
        return email;
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
