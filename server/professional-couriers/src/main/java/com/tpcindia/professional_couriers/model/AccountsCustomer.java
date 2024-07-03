package com.tpcindia.professional_couriers.model;

import jakarta.persistence.*;

@Entity
@Table(name = "AccountsCustomer", schema = "dbo")
public class AccountsCustomer {

    @Id
    @Column(name = "CustCode", nullable = false)
    private String custCode;

    @Column(name = "FirmName")
    private String firmName;





}
