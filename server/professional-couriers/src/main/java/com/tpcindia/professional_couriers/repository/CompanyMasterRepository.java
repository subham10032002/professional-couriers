package com.tpcindia.professional_couriers.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tpcindia.professional_couriers.model.CompanyMaster;
import org.springframework.data.repository.query.Param;

public interface CompanyMasterRepository extends JpaRepository<CompanyMaster, Long> {

    @Query("SELECT c FROM CompanyMaster c WHERE c.code = :code")
    CompanyMaster findAddressDetailsByCode(String code);

    @Query("SELECT c.emailPassword FROM CompanyMaster c WHERE c.defaultType = 'Yes'")
    String getEmailPassword();
}