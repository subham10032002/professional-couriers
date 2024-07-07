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

    @Column(name = "Type")
    private String type;

    @Column(name = "Flag")
    private String flag;

    @Column(name = "EmailId")
    private String emailId;

    public String getEmailId() {
        return emailId;
    }

}
