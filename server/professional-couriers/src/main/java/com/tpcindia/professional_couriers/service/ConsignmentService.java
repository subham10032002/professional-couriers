package com.tpcindia.professional_couriers.service;

import com.tpcindia.professional_couriers.repository.BookAllotRepository;
import com.tpcindia.professional_couriers.repository.BookTransRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ConsignmentService {

    @Autowired
    private BookAllotRepository bookAllotRepository;

    @Autowired
    private BookTransRepository bookTransRepository;

    public Map<String, Object> getNextConsignmentNumber(String branch, String custCode) throws IllegalArgumentException {
        String accCode = bookAllotRepository.findAccCodeByFirmName(branch);
        
        if (accCode == null) {
            throw new IllegalArgumentException("No book allotment found for the branch");
        }

        Long finalConsignmentNumber = (long) 0;
        // Find the last used consignment number
        Long lastUsedConsNo = bookTransRepository.findMaxAccNoCredit(custCode);
        Integer balanceStock = bookTransRepository.countUnusedAccNosCredit(custCode);

        if (balanceStock == null || balanceStock <= 2) {
            throw new IllegalArgumentException("Book Not Issued");
        }

        if (lastUsedConsNo == null || lastUsedConsNo == 0) {
            lastUsedConsNo = bookTransRepository.findMinUnusedAccNoCredit(custCode);
        }

        if (lastUsedConsNo == null || lastUsedConsNo == 0) {
            finalConsignmentNumber = (long) 0;
        } else {
            finalConsignmentNumber = lastUsedConsNo + 1;
        }

        Map<String, Object> result = new HashMap<>();
        result.put("accCode", accCode);
        result.put("consignmentNo", finalConsignmentNumber);
        result.put("balanceStock", balanceStock);

        return result;
    }
}

