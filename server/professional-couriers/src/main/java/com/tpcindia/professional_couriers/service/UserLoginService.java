package com.tpcindia.professional_couriers.service;

import com.tpcindia.professional_couriers.utils.EncryptionUtils;
import com.tpcindia.professional_couriers.utils.exceptions.InvalidCredentialsException;
import com.tpcindia.professional_couriers.model.UserLogin;
import com.tpcindia.professional_couriers.repository.UserLoginRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserLoginService {

    @Autowired
    private UserLoginRepository userLoginRepository;

    public UserLogin authenticateUser(String loginId, String password) throws InvalidCredentialsException {
        UserLogin userLogin = userLoginRepository.findByLoginIdAndPassword(loginId, getDecryptedPassword(password), "active");

        if (userLogin == null) {
            throw new InvalidCredentialsException("Invalid Credentials");
        }
        return userLogin;
    }

    private String getDecryptedPassword(String password) {
        EncryptionUtils encryptionUtils = new EncryptionUtils();
        return encryptionUtils.decrypt(password);
    }
}
