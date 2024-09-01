package com.tpcindia.professional_couriers.repository;

import com.tpcindia.professional_couriers.dto.responsedto.FirmDetailsDTO;
import com.tpcindia.professional_couriers.model.AccountsCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountsCustomerRepository extends JpaRepository<AccountsCustomer, String> {

    @Query("SELECT new com.tpcindia.professional_couriers.dto.responsedto.FirmDetailsDTO(ac.firmName, TRIM(ac.address), TRIM(ac.contactNo), TRIM(ac.masterCompanyCode)) FROM AccountsCustomer ac WHERE ac.type = 'Cust' AND ac.branch = :branch AND ac.flag = 'Acti' ORDER BY ac.firmName")
    List<FirmDetailsDTO> findFirmDetailsByTypeAndBranch(@Param("branch") String branch);

    @Query("SELECT ac.firmName FROM AccountsCustomer ac WHERE ac.custCode = :branchCode AND ac.flag = 'Acti'")
    String findBranchByBranchCode(@Param("branchCode") String branchCode);

    @Query("SELECT ac.emailId FROM AccountsCustomer ac WHERE ac.type = 'Cust' AND ac.custCode = :custCode AND ac.flag = 'Acti'")
    String findEmailByCustCodeAndType(@Param("custCode") String custCode);

    @Query("SELECT ac.emailId FROM AccountsCustomer ac WHERE ac.type = 'Bran' AND ac.custCode = :branchCode AND ac.flag = 'Acti'")
    String findEmailByBranchCodeAndType(@Param("branchCode") String branchCode);

    @Query("SELECT ac.custCode FROM AccountsCustomer ac WHERE ac.type = 'Cust' AND ac.branch = :branch AND ac.flag = 'Acti' AND ac.firmName = :firmName ORDER BY ac.firmName")
    String findCustCodeByFirmNameAndBranch(String branch, String firmName);

}