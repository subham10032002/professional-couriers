package com.tpcindia.professional_couriers.service;

import com.tpcindia.professional_couriers.repository.AccountsCustomerRepository;
import com.tpcindia.professional_couriers.utils.EncryptionUtils;
import com.tpcindia.professional_couriers.utils.exceptions.InvalidCredentialsException;
import com.tpcindia.professional_couriers.model.UserLogin;
import com.tpcindia.professional_couriers.repository.UserLoginRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class UserLoginService {

    @Autowired
    private UserLoginRepository userLoginRepository;

    @Autowired
    private AccountsCustomerRepository accountsCustomerRepository;

    public Map<String, Object> authenticateUser(String loginId, String password) throws InvalidCredentialsException {
        UserLogin userLogin = userLoginRepository.findByLoginIdAndPassword(loginId, getEncryptedPassword(password), "active");

        if (userLogin == null) {
            throw new InvalidCredentialsException("Invalid Credentials");
        }

        String branch = accountsCustomerRepository.findBranchByBranchCode(userLogin.getBranchCode());
        if (branch != null) {
            branch = branch.trim();
        }

        Map<String, Object> result = new HashMap<>();
        result.put("firstName", userLogin.getFirstName());
        result.put("lastName", userLogin.getLastName());
        result.put("branchCode", userLogin.getBranchCode());
        result.put("branch", branch);
        result.put("userCode", userLogin.getUserCode());

        return result;
    }

    private String getEncryptedPassword(String password) {
        EncryptionUtils encryptionUtils = new EncryptionUtils();
        return encryptionUtils.encrypt(password);
    }
}
