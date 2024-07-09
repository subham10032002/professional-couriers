package com.tpcindia.professional_couriers.service;

import com.tpcindia.professional_couriers.repository.DestinationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DestinationService {

    private final DestinationRepository destinationRepository;

    public DestinationService(DestinationRepository repository) {
        this.destinationRepository = repository;
    }

    public List<String> getCitiesByPinCode(String pinCode) {
        List<String> cities = destinationRepository.findCitiesByPinCode(pinCode);
        cities.replaceAll(String::trim);
        return cities;
    }
}
