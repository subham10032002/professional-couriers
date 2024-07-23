package com.tpcindia.professional_couriers.repository;

import com.tpcindia.professional_couriers.model.CreditBookingData;
import com.tpcindia.professional_couriers.model.UserLogin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreditBookingDataRepository extends JpaRepository<CreditBookingData, Long> {

    @Query("SELECT u FROM CreditBookingData u WHERE u.branch = :branch AND u.emailSent = 'No'")
    List<CreditBookingData> findCreditBookingDataByBranch(String branch);
}
