package com.tpcindia.professional_couriers.service;

import com.tpcindia.professional_couriers.repository.AccountsCustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountsCustomerService {

    @Autowired
    private AccountsCustomerRepository accountsCustomerRepository;

    public List<String> getFirmNamesByBranch(String branchCode) {
        return accountsCustomerRepository.findFirmNamesByTypeAndBranch(branchCode);
    }
}
