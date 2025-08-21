package com.tpcindia.professional_couriers.service;

import com.tpcindia.professional_couriers.model.CreditBookingData;
import com.tpcindia.professional_couriers.repository.AccountsCustomerRepository;
import com.tpcindia.professional_couriers.repository.CompanyMasterRepository;
import com.tpcindia.professional_couriers.repository.CreditBookingDataRepository;

import jakarta.mail.internet.MimeMessage;

import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private AccountsCustomerRepository accountsCustomerRepository;

    @Autowired
    private CreditBookingDataRepository creditBookingDataRepository;

    @Autowired
    private CompanyMasterRepository companyMasterRepository;

    public ResponseEntity<?> sendEmails(String branch, String userName, String branchCode, String userCode) {
        try {
            List<CreditBookingData> bookings = creditBookingDataRepository.findCreditBookingDataByBranch(branch, userCode, getCurrentDate());

            if (bookings.isEmpty()) {
                return new ResponseEntity<>("There is no pending row left", HttpStatus.NOT_FOUND);
            }

            // Group by firmName
        Map<String, List<CreditBookingData>> groupedByFirmName = bookings.stream()
                .collect(Collectors.groupingBy(CreditBookingData::getClientName));

        // Iterate through each group, create an Excel file, and send it via email
        for (Map.Entry<String, List<CreditBookingData>> entry : groupedByFirmName.entrySet()) {
            String firmName = entry.getKey();
            List<CreditBookingData> firmBookings = entry.getValue();

            String custCode = firmBookings.get(0).getCustCode(); // Assuming custCode is the same for the entire firm

            String customerEmail = null;
            String branchEmail = null;

            if (custCode != null && !custCode.isEmpty()) {
                customerEmail = accountsCustomerRepository.findEmailByCustCodeAndType(custCode);
            }
            if (branchCode != null && !branchCode.isEmpty()) {
                branchEmail = accountsCustomerRepository.findEmailByBranchCodeAndType(branchCode);
            }

            if (customerEmail == null || branchEmail == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer email and/or branch email are missing for firm: " + firmName);
            }


            // Create a new Excel workbook and sheet
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Bookings");

            // Write header row
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Booking Date");
            headerRow.createCell(1).setCellValue("Consignment Number");
            headerRow.createCell(2).setCellValue("Destination");
            headerRow.createCell(3).setCellValue("Consignee");
            headerRow.createCell(4).setCellValue("Psc");
            headerRow.createCell(5).setCellValue("Weight");
            headerRow.createCell(6).setCellValue("PinCode");

            // Write data rows
            int rowNum = 1;
            for (CreditBookingData booking : firmBookings) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(booking.getBookingDate().toString());
                row.createCell(1).setCellValue(booking.getConsignmentNumber());
                row.createCell(2).setCellValue(booking.getDestination());
                row.createCell(3).setCellValue(booking.getConsigneeName());
                row.createCell(4).setCellValue(booking.getNoOfPsc());
                row.createCell(5).setCellValue(booking.getWeight());
                row.createCell(6).setCellValue(booking.getPincode());
            }

            // Adjust column widths
            for (int i = 0; i < 7; i++) {
                sheet.autoSizeColumn(i);
            }

            // Save the Excel file with the firmName as the filename
            String fileName = firmName + ".xlsx";
            File excelFile = new File(fileName);
            try (FileOutputStream fileOut = new FileOutputStream(excelFile)) {
                workbook.write(fileOut);
            }

            // Close the workbook
            workbook.close();

            // Fetch PDFs from the database and combine them
            byte[] combinedPdf = combinePdfs(firmBookings);

            // Send the Excel file via email
            boolean emailSent = sendEmail(customerEmail, branchEmail, excelFile, firmName, combinedPdf);

            if (emailSent) {
                for (CreditBookingData booking : firmBookings) {
                    booking.setEmailSent("Yes");
                    booking.setDateOfEmailSent(getCurrentDate());
                    booking.setEmailSenderUsername(userName);
                    booking.setPdfAddress(null);
                    creditBookingDataRepository.save(booking);
                }
            } else {
                excelFile.delete();
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email not sent for firm: " + firmName);
            }

            // Optionally delete the file after sending the email
            excelFile.delete();
        }

        return ResponseEntity.ok("Emails sent successfully for all firms.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }

    private boolean sendEmail(String to, String cc, File file, String clientName, byte[] combinedPdf) {
        try {
            String emailPassword = companyMasterRepository.getEmailPassword();

            if (emailPassword != null) {
                emailPassword = emailPassword.trim();
            }

            JavaMailSenderImpl mailSender = (JavaMailSenderImpl) javaMailSender;

            // Override the password dynamically
            mailSender.setPassword(emailPassword);

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(to);
            helper.setCc(cc);
            helper.setSubject("Credit Auto booking  - " + clientName + " - "  + getCurrentDate());
            helper.setText("Dear Customer, \nAttached here are the bookings done using mobile app");

            FileSystemResource fileResource = new FileSystemResource(file);
            helper.addAttachment(file.getName(), fileResource);

            ByteArrayResource pdfResource = new ByteArrayResource(combinedPdf);
            helper.addAttachment(clientName + ".pdf", pdfResource);

            mailSender.send(message);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return sdf.format(new Date());
    }

    private byte[] combinePdfs(List<CreditBookingData> creditBookingData) throws Exception {
        PDFMergerUtility pdfMergerUtility = new PDFMergerUtility();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        for (CreditBookingData booking : creditBookingData) {
            if (booking.getPdfAddress() == null) continue;
            try (ByteArrayInputStream inputStream = new ByteArrayInputStream(booking.getPdfAddress())) {
                pdfMergerUtility.addSource(inputStream);
            }
        }

        pdfMergerUtility.setDestinationStream(outputStream);
        pdfMergerUtility.mergeDocuments(null);

        return outputStream.toByteArray();

    }
}
