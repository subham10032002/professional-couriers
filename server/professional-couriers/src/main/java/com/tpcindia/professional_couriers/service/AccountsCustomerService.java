package com.tpcindia.professional_couriers.service;

import com.tpcindia.professional_couriers.dto.responsedto.FirmDetailsDTO;
import com.tpcindia.professional_couriers.repository.AccountsCustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AccountsCustomerService {

    @Autowired
    private AccountsCustomerRepository accountsCustomerRepository;

    public List<FirmDetailsDTO> getFirmDetailsByBranch(String branch) {
        List<FirmDetailsDTO> firmDetails = accountsCustomerRepository.findFirmDetailsByTypeAndBranch(branch);
        firmDetails.forEach(firmDetail -> {
            firmDetail.setFirmName(firmDetail.getFirmName().trim());
            firmDetail.setAddress(firmDetail.getAddress().trim());
        });
        return firmDetails;
    }

    public String findBranchByBranchCode(String branchCode) {
        String branch = accountsCustomerRepository.findBranchByBranchCode(branchCode);
        if (branch != null) {
            return branch.trim();
        } else {
            return null;
        }
    }

    public String findEmailByBranch(String custCode) {
        String email = accountsCustomerRepository.findEmailByCustCodeAndType(custCode);
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
