package com.tpcindia.professional_couriers.repository;

import com.tpcindia.professional_couriers.model.AccountsCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountsCustomerRepository extends JpaRepository<AccountsCustomer, Long> {
    // TODO:- Check the flag name before releasing
    @Query("SELECT ac.firmName FROM AccountsCustomer ac WHERE ac.type = 'Cust' AND ac.branch = :branch AND ac.flag = 'Acti' ORDER BY ac.firmName")
    List<String> findFirmNamesByTypeAndBranch(@Param("branch") String branch);

    @Query("SELECT ac.branch FROM AccountsCustomer ac WHERE ac.custCode = :branchCode AND ac.flag = 'Acti'")
    String findBranchByBranchCode(@Param("branchCode") String branchCode);

    @Query("SELECT ac.emailId FROM AccountsCustomer ac WHERE ac.type = 'Cust' AND ac.custCode = :custCode AND ac.flag = 'Acti'")
    String findEmailByBranchAndType(@Param("custCode") String custCode);

    @Query("SELECT ac.emailId FROM AccountsCustomer ac WHERE ac.type = 'Bran' AND ac.custCode = :branchCode AND ac.flag = 'Acti'")
    String findEmailByBranchCodeAndType(@Param("branchCode") String branchCode);

}