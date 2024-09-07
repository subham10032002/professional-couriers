package com.tpcindia.professional_couriers.controller;

import com.tpcindia.professional_couriers.dto.EmailDTO;
import com.tpcindia.professional_couriers.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/send")
    public ResponseEntity<?> sendEmails(@RequestBody EmailDTO emailDTO) {
        return emailService.sendEmails(
            emailDTO.getBranch(),
            emailDTO.getUserName(),
            emailDTO.getBranchCode(),
            emailDTO.getUsercode()
        );
    }
}
