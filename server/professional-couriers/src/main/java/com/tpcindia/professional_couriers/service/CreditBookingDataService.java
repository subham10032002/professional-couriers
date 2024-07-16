package com.tpcindia.professional_couriers.service;

import com.tpcindia.professional_couriers.dto.BranchDTO;
import com.tpcindia.professional_couriers.dto.CBDataFetchDTO;
import com.tpcindia.professional_couriers.dto.CreditBookingDataDTO;
import com.tpcindia.professional_couriers.model.CreditBookingData;
import com.tpcindia.professional_couriers.repository.CreditBookingDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreditBookingDataService {

    @Autowired
    private CreditBookingDataRepository creditBookingDataRepository;

    public void saveCreditBookingData(CreditBookingDataDTO dataDTO) {
        creditBookingDataRepository.save(mapToEntity(dataDTO));
    }

    public List<CreditBookingData> getCreditBookingData(String branch) {
        return creditBookingDataRepository.findCreditBookingDataByBranch(branch);
    }

    private CreditBookingData mapToEntity(CreditBookingDataDTO dto) {
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
        return entity;
    }
}
