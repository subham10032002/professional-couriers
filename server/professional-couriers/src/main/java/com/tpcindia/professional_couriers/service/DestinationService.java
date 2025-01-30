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
            for (Destination destination : destinations) {
                DestinationDetailsDTO destinationDetailsDTO = new DestinationDetailsDTO();
                destinationDetailsDTO.setCity(safeTrim(destination.getCity()));
                destinationDetailsDTO.setDestCode(safeTrim(destination.getDestCode()));
                destinationDetailsDTO.setDestn(safeTrim(destination.getDestn()));
                destinationDetailsDTO.setHub(safeTrim(destination.getHub()));
                destinationDetailsDTO.setState(safeTrim(destination.getState()));
                destinationDetailsDTO.setAreaCode(safeTrim(destination.getAreaCode()));
                String city = null;
                if (destination.getHub() == null || safeTrim(destination.getHub()).isEmpty()) {
                    if (destination.getDestn() == null || safeTrim(destination.getDestn()).isEmpty()) {
                        if (destination.getAreaCode() == null || safeTrim(destination.getAreaCode()).isEmpty()) {
                            city = destination.getCity();
                        } else {
                            List<String> cityByAreacode = destinationRepository.findCityByAreaCode(destination.getAreaCode());
                            if (!cityByAreacode.isEmpty()) {
                                city = cityByAreacode.get(0);
                            }
                        }
                    } else {
                        List<String> cityByDestn = destinationRepository.findCitiesByDestn(destination.getDestn());
                        if (!cityByDestn.isEmpty()) {
                            city = cityByDestn.get(0);
                        }
                    }
                } else {
                    List<String> cityByHub = destinationRepository.findCitiesByHub(destination.getHub());
                    if (!cityByHub.isEmpty()) {
                        city = cityByHub.get(0);
                    }
                }
                if (city == null || city.isEmpty()) {
                    destinationDetailsDTO.setPdfCity(safeTrim(destination.getCity()));
                } else {
                    destinationDetailsDTO.setPdfCity(safeTrim(city));
                }
                destinationResponse.add(destinationDetailsDTO);
            }
        }
        return ResponseEntity.ofNullable(destinationResponse);
    }

    /**
     * Safely trims a string, returning an empty string if the input is null.
     *
     * @param input the string to trim
     * @return a trimmed string or an empty string if the input is null
     */
    private String safeTrim(String input) {
        return input == null ? "" : input.trim();
    }
}
