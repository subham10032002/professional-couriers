package com.tpcindia.professional_couriers.repository;

import com.tpcindia.professional_couriers.model.CreditBookingData;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CreditBookingDataRepository extends JpaRepository<CreditBookingData, Long> {

    @Query("SELECT u FROM CreditBookingData u WHERE u.branch = :branch AND u.emailSent = 'No' AND u.userCode = :userCode AND u.bookingDate = :bookingDate ORDER BY u.clientName")
    List<CreditBookingData> findCreditBookingDataByBranch(String branch, String userCode, String bookingDate);

    @Query("SELECT COUNT(t) > 0 FROM CreditBookingData t WHERE t.transactionId = :transactionId")
    boolean existsByTransactionId(@Param("transactionId") String transactionId);

    @Query("SELECT c FROM CreditBookingData c WHERE c.branch = :branch AND c.userCode = :userCode ORDER BY c.consignmentNumber DESC")
    List<CreditBookingData> findTop20(Pageable pageable, @Param("branch") String branch, @Param("userCode") String userCode);
}
