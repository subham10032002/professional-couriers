package com.tpcindia.professional_couriers.repository;

import com.tpcindia.professional_couriers.model.Destination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DestinationRepository extends JpaRepository<Destination, String> {

    @Query("SELECT d FROM Destination d WHERE d.pincode = :pinCode ORDER BY d.city")
    List<Destination> findDestinaionsByPinCode(String pinCode);

}
