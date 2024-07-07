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
    @Query("SELECT ac.firmName FROM AccountsCustomer ac WHERE ac.type = 'Cust' AND ac.custCode = :branchCode AND ac.flag = 'Acti' ORDER BY ac.firmName")
    List<String> findFirmNamesByTypeAndBranch(@Param("branchCode") String branchCode);

    @Query("SELECT ac.emailId FROM AccountsCustomer ac WHERE ac.type = 'Cust' AND ac.firmName = :firmName AND ac.flag = 'Acti'")
    String findEmailByFirmNameAndType(@Param("firmName") String firmName);
}