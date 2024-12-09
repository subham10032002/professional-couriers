package com.tpcindia.professional_couriers.service;

import com.tpcindia.professional_couriers.dto.CreditBookingDataDTO;
import com.tpcindia.professional_couriers.model.CreditBookingData;
import com.tpcindia.professional_couriers.repository.AccountsCustomerRepository;
import com.tpcindia.professional_couriers.repository.BookTransRepository;
import com.tpcindia.professional_couriers.repository.CreditBookingDataRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
        return entity;
    }
}
