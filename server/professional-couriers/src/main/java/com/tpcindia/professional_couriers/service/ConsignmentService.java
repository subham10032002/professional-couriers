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

    public Map<String, Object> getNextConsignmentNumber(String branch) throws Exception {
        String accCode = bookAllotRepository.findAccCodeByFirmName(branch);
        Long startNo = bookAllotRepository.findStartNoByFirmName(branch);

        if (accCode == null || startNo == null) {
            throw new IllegalArgumentException("No book allotment found for the branch");
        }

        // Find the last used consignment number
        Long lastUsedConsNo = bookTransRepository.findMaxAccNoCredit(startNo);
        Integer balanceStock = bookTransRepository.countUnusedAccNosCredit(startNo);

        if (lastUsedConsNo == null || lastUsedConsNo == 0) {
            // Find the minimum unused consignment number
            lastUsedConsNo = bookTransRepository.findMinUnusedAccNoCredit(startNo);
        } else {
            lastUsedConsNo += 1;
        }

        Map<String, Object> result = new HashMap<>();
        result.put("startNo", startNo);
        result.put("accCode", accCode);
        result.put("consignmentNo", lastUsedConsNo);
        result.put("balanceStock", balanceStock);

        return result;
    }
}

