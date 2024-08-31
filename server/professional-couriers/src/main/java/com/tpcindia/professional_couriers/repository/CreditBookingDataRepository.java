package com.tpcindia.professional_couriers.repository;

import com.tpcindia.professional_couriers.model.CreditBookingData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreditBookingDataRepository extends JpaRepository<CreditBookingData, Long> {

    @Query("SELECT u FROM CreditBookingData u WHERE u.branch = :branch AND u.emailSent = 'No' AND u.userCode = :userCode AND u.bookingDate = :bookingDate ORDER BY u.clientName")
    List<CreditBookingData> findCreditBookingDataByBranch(String branch, String userCode, String bookingDate);
}
