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

    @Column(name = "Branch")
    private String branch;

    @Column(name = "Address")
    private String address;

    @Column(name = "ContactNo")
    private String contactNo;

    public String getEmailId() {
        return emailId;
    }

    public String getAddress() {
        return address;
    }

}
