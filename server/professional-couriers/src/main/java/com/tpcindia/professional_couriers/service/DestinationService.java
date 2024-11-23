package com.tpcindia.professional_couriers.service;

import com.tpcindia.professional_couriers.dto.responsedto.DestinationDetailsDTO;
import com.tpcindia.professional_couriers.model.Destination;
import com.tpcindia.professional_couriers.repository.DestinationRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        List<DestinationDetailsDTO> destinationResponse = new ArrayList<>();
        if (!destinations.isEmpty()) {
            for (Destination destination: destinations) {
                DestinationDetailsDTO destinationDetailsDTO = new DestinationDetailsDTO();
                destinationDetailsDTO.setCity(destination.getCity().trim());
                destinationDetailsDTO.setDestCode(destination.getDestCode().trim());
                destinationDetailsDTO.setDestn(destination.getDestn().trim());
                destinationDetailsDTO.setHub(destination.getHub().trim());
                destinationDetailsDTO.setState(destination.getState().trim());
                destinationDetailsDTO.setAreaCode(destination.getAreaCode().trim());
                if (destination.getAreaCode() != null && destination.getAreaCode().trim().isEmpty()) {
                    if (destination.getHub() != null && destination.getHub().trim().isEmpty()) {
                        destinationDetailsDTO.setPdfCity(destination.getCity().trim());
                    } else {
                        String cityByHub = destinationRepository.findCityByHub(destination.getHub());
                        destinationDetailsDTO.setPdfCity(cityByHub.trim());
                    }
                } else {
                    String cityByAreaCode = destinationRepository.findCityByAreaCode(destination.getAreaCode());
                    destinationDetailsDTO.setPdfCity(cityByAreaCode.trim());
                }
                destinationResponse.add(destinationDetailsDTO);
            }
        }
        return ResponseEntity.ofNullable(destinationResponse);
    }
}
