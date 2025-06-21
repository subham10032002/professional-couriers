package com.tpcindia.professional_couriers.service;

import com.tpcindia.professional_couriers.dto.CreditBookingDataDTO;
import com.tpcindia.professional_couriers.dto.responsedto.TopPDFDetailsDTO;
import com.tpcindia.professional_couriers.model.CreditBookingData;
import com.tpcindia.professional_couriers.repository.AccountsCustomerRepository;
import com.tpcindia.professional_couriers.repository.BookTransRepository;
import com.tpcindia.professional_couriers.repository.CreditBookingDataRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Transactional
public class CreditBookingDataService {

    @Autowired
    private CreditBookingDataRepository creditBookingDataRepository;

    @Autowired
    private AccountsCustomerRepository accountsCustomerRepository;

    @Autowired
    private BookTransRepository bookTransRepository;

    public ResponseEntity<?> saveCreditBookingData(CreditBookingDataDTO dataDTO) {
        boolean check = creditBookingDataRepository.existsByTransactionId(dataDTO.getTransactionId());

        if (check) return new ResponseEntity<>("Credit booking data already exist", HttpStatus.BAD_REQUEST);

        String custCode = accountsCustomerRepository.findCustCodeByFirmNameAndBranch(
                dataDTO.getBranch(),
                dataDTO.getClientName()
        );
        if (custCode != null) {
            
            String accCode = dataDTO.getConsignmentNumber().substring(0,3);
            Long accNo = Long.parseLong(dataDTO.getConsignmentNumber().substring(3));
            
            int rowsUpdated = bookTransRepository.updateBookTransCounter("Yes", accNo, accCode);
            if (rowsUpdated > 0) {
                creditBookingDataRepository.save(mapToEntity(custCode, dataDTO));
                return new ResponseEntity<>("Credit booking data submitted successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Credit booking data not submitted: Book is not alloted", HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>("Credit booking data not submitted: Customer Code Not found", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<?> getCreditBookingData(String branch, String userCode) {
        Pageable pageable = PageRequest.of(0, 20);
        List<CreditBookingData> results = creditBookingDataRepository.findTop20(pageable, branch, userCode);
        List<TopPDFDetailsDTO> response = new ArrayList<>();
        try {
            for (CreditBookingData data : results) {
                String encodedPdf = "";
                String transactionId = "";
                String consignmentNumber = "";
                if (data.getPdfAddress() != null) {
                    encodedPdf = Base64.getEncoder().encodeToString(data.getPdfAddress());
                }
                if (data.getTransactionId() != null) {
                    transactionId = data.getTransactionId().trim();
                }
                if (data.getConsignmentNumber() != null) {
                    consignmentNumber = data.getConsignmentNumber().trim();
                }
                 TopPDFDetailsDTO topPDFDetailsDTO = new TopPDFDetailsDTO(
                         transactionId,
                         consignmentNumber,
                         encodedPdf
                 );
                response.add(topPDFDetailsDTO);
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return ResponseEntity.ok(response);
    }

    private CreditBookingData mapToEntity(String custCode, CreditBookingDataDTO dto) {
        CreditBookingData entity = new CreditBookingData();
        entity.setCurrentDate(dto.getCurrentDate());
        entity.setConsignmentNumber(dto.getConsignmentNumber());
        entity.setBalanceStock(dto.getBalanceStock());
        entity.setClientName(dto.getClientName());
        entity.setBookingDate(dto.getBookingDate());
        entity.setPincode(dto.getPincode());
        entity.setDestination(dto.getDestination());
        entity.setConsigneeType(dto.getConsigneeType());
        entity.setMode(dto.getMode());
        entity.setConsigneeName(dto.getConsigneeName());
        entity.setNoOfPsc(dto.getNoOfPsc());
        entity.setWeight(dto.getWeight());
        entity.setUnit(dto.getUnit());
        entity.setLength(dto.getLength());
        entity.setWidth(dto.getWidth());
        entity.setHeight(dto.getHeight());
        entity.setInvoiceNumber(dto.getInvoiceNumber());
        entity.setProduct(dto.getProduct());
        entity.setDeclaredValue(dto.getDeclaredValue());
        entity.setBranch(dto.getBranch());
        entity.setEwayBill(dto.getEwayBill());
        entity.setPhotoOfAddress(dto.getPhotoOfAddress());
        entity.setCustCode(custCode);
        entity.setEmailSent("No");
        entity.setUserName(dto.getUsername());
        entity.setDateOfEmailSent("");
        entity.setEmailSenderUsername("");
        entity.setSync("No");
        entity.setUserCode(dto.getUserCode());
        entity.setLongitude(dto.getLongitude());
        entity.setLatitude(dto.getLatitude());
        entity.setPdfAddress(dto.getPdfAddress());
        entity.setDestCode(dto.getDestCode());
        entity.setTransactionId(dto.getTransactionId());
        return entity;
    }
}
