package com.tpcindia.professional_couriers.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tpcindia.professional_couriers.model.Destination;

@Repository
public interface DestinationRepository extends JpaRepository<Destination, String> {

    @Query("SELECT d FROM Destination d WHERE d.pincode = :pinCode ORDER BY d.city")
    List<Destination> findDestinaionsByPinCode(String pinCode);

//    @Query("SELECT d.city FROM Destination d WHERE d.branchType = 'A1' AND d.areaCode = :areaCode")
//    String findCityByAreaCode(String areaCode);

//    @Query("SELECT d.city FROM Destination d WHERE d.branchType = 'A1' AND d.areaCode = :hub")
//    String findCityByHub(String hub);

//    @Query("""
//    SELECT d.city
//    FROM Destination d
//    WHERE d.branchType = 'A1' AND d.areaCode = :hub
//    ORDER BY CASE WHEN d.pincode = '0' THEN 1 ELSE 0 END, d.destCode ASC
//""")
//    Optional<String> findTopCityByHub(String hub);


    @Query("""
    SELECT d.city 
    FROM Destination d 
    WHERE d.branchType = 'A1' AND d.areaCode = :hub 
    ORDER BY CASE WHEN d.pincode = '0' THEN 1 ELSE 0 END, d.destCode ASC
""")
    List<String> findCitiesByHub(String hub);

    @Query("SELECT d.city FROM Destination d WHERE d.branchType = 'A1' AND d.areaCode = :areaCode ORDER BY CASE WHEN d.pincode = '0' THEN 1 ELSE 0 END, d.destCode ASC")
    List<String> findCityByAreaCode(String areaCode);

    @Query("""
    SELECT d.city 
    FROM Destination d 
    WHERE d.branchType = 'A1' AND d.areaCode = :destn 
    ORDER BY CASE WHEN d.pincode = '0' THEN 1 ELSE 0 END, d.destCode ASC
""")
    List<String> findCitiesByDestn(String destn);


}
