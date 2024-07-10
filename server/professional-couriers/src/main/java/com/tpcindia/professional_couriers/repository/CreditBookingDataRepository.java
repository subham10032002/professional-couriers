package com.tpcindia.professional_couriers.repository;

import com.tpcindia.professional_couriers.model.CreditBookingData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditBookingDataRepository extends JpaRepository<CreditBookingData, Long> {

}
