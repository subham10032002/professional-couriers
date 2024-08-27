package com.tpcindia.professional_couriers.service;

import com.tpcindia.professional_couriers.model.Destination;
import com.tpcindia.professional_couriers.repository.DestinationRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DestinationService {

    private final DestinationRepository destinationRepository;

    public DestinationService(DestinationRepository repository) {
        this.destinationRepository = repository;
    }

    public Map<String, Object> getDestinationsByPinCode(String pinCode) {
        List<Destination> destinations = destinationRepository.findDestinaionsByPinCode(pinCode);
        Map<String, Object> result = new HashMap<>();
        for (Destination destination: destinations) {
            result.put(destination.getCity().trim(), destination.getDestCode().trim());
        }
        return result;
    }
}
