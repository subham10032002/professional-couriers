package com.tpcindia.professional_couriers.repository;

import com.tpcindia.professional_couriers.model.BookAllot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookAllotRepository extends JpaRepository<BookAllot, Long> {
    // TODO:- Change all the Cast to Credit before release
    @Query("SELECT bo.accCode FROM BookAllot bo, AccountsCustomer ac WHERE ac.firmName = :branch AND ac.custCode = bo.branchCode AND bo.type = 'Auto Docket Credit' ORDER BY bo.startNo")
    String findAccCodeByFirmName(@Param("branch") String branch);

    @Query("SELECT bo.startNo FROM BookAllot bo, AccountsCustomer ac WHERE ac.firmName = :branch AND ac.custCode = bo.branchCode AND bo.type = 'Auto Docket Credit' ORDER BY bo.startNo")
    Long findStartNoByFirmName(@Param("branch") String branch);

    List<BookAllot> findByBranchCodeAndTypeOrderByStartNo(String branchCode, String type);
}
