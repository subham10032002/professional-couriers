package com.tpcindia.professional_couriers.service;

import com.tpcindia.professional_couriers.model.Destination;
import com.tpcindia.professional_couriers.repository.DestinationRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DestinationService {

    private final DestinationRepository destinationRepository;

    public DestinationService(DestinationRepository repository) {
        this.destinationRepository = repository;
    }

    public ResponseEntity<?> getDestinationsByPinCode(String pinCode) {
        List<Destination> destinations = destinationRepository.findDestinaionsByPinCode(pinCode);
        if (destinations == null) {
            return new ResponseEntity<>("No Destinations Found for the given pincode", HttpStatus.NOT_FOUND);
        }
        for (Destination destination: destinations) {
            destination.setCity(destination.getCity().trim());
            destination.setDestCode(destination.getDestCode().trim());
            destination.setDestn(destination.getDestn().trim());
            destination.setHub(destination.getHub().trim());
            destination.setState(destination.getState().trim());
            destination.setAreaCode(destination.getAreaCode().trim());
        }
        return ResponseEntity.ofNullable(destinations);
    }
}
