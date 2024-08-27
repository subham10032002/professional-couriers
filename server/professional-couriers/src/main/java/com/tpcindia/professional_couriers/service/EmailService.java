package com.tpcindia.professional_couriers.service;

import com.tpcindia.professional_couriers.model.CreditBookingData;
import com.tpcindia.professional_couriers.repository.AccountsCustomerRepository;
import com.tpcindia.professional_couriers.repository.CreditBookingDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import org.springframework.mail.javamail.JavaMailSender;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private AccountsCustomerRepository accountsCustomerRepository;

    @Autowired
    private CreditBookingDataRepository creditBookingDataRepository;

    public ResponseEntity<String> sendEmails(String branch, String userName, String branchCode) {
        try {
            List<CreditBookingData> bookings = creditBookingDataRepository.findCreditBookingDataByBranch(branch);

            if (bookings.isEmpty()) {
                return new ResponseEntity<>("There is no pending row left", HttpStatus.NOT_FOUND);
            }

            for (CreditBookingData booking : bookings) {
                String custCode = booking.getCustCode();
                String customerEmail = null;
                String branchEmail = null;
                if (custCode != null && !custCode.isEmpty()) {
                    customerEmail = accountsCustomerRepository.findEmailByCustCodeAndType(custCode);
                }
                if (branchCode != null && !branchCode.isEmpty()) {
                    branchEmail = accountsCustomerRepository.findEmailByBranchCodeAndType(branchCode);
                }

                if (customerEmail == null || branchEmail == null) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("customerEmail and branchEmail are missing");
                }

                // boolean emailSent = sendEmail(customerEmail, branchEmail, booking);

                boolean emailSent = sendEmail("Epod@atlantglobalindia.com", "Epod@atlantglobalindia.com", booking);

                if (emailSent) {
                    booking.setEmailSent("Yes");
                    booking.setDateOfEmailSent(getCurrentDate());
                    booking.setEmailSenderUsername(userName);
                    creditBookingDataRepository.save(booking);
                } else {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email not sent");
                }
            }
            return ResponseEntity.status(HttpStatus.OK).body("Mail sent successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }

    private boolean sendEmail(String to, String cc, CreditBookingData data) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            // message.setCc(cc);
            message.setSubject("Subject");
            message.setText("Booking details: " + data.toString());
            javaMailSender.send(message);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        return sdf.format(new Date());
    }
}
