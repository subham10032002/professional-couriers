package com.tpcindia.professional_couriers.controller;

import com.tpcindia.professional_couriers.dto.AuthenticationRequestDTO;
import com.tpcindia.professional_couriers.utils.exceptions.InvalidCredentialsException;
import com.tpcindia.professional_couriers.model.UserLogin;
import com.tpcindia.professional_couriers.service.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/users")
public class UserLoginController {

    @Autowired
    private UserLoginService userLoginService;

    @PostMapping("/authenticate")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequestDTO requestDTO) {
        try {
            UserLogin userLogin = userLoginService.authenticateUser(requestDTO.getLoginId(), requestDTO.getPassword());
            return new ResponseEntity<>(userLogin, HttpStatus.OK);
        } catch (InvalidCredentialsException e) {
            return new ResponseEntity<>("Invalid credentials", HttpStatus.UNAUTHORIZED);
        }
    }
}
