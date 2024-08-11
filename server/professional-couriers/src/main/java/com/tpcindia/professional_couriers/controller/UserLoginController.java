package com.tpcindia.professional_couriers.controller;

import com.tpcindia.professional_couriers.dto.AuthenticationRequestDTO;
import com.tpcindia.professional_couriers.service.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/users")
public class UserLoginController {

    @Autowired
    private UserLoginService userLoginService;

    @PostMapping("/authenticate")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequestDTO requestDTO) {

        Map<String, Object> result = new HashMap<>();
        try {
            result = userLoginService.authenticateUser(requestDTO.getLoginId(), requestDTO.getPassword());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ofNullable(result);
    }
}
