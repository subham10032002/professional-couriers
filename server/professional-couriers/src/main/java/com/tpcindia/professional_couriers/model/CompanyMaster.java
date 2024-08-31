package com.tpcindia.professional_couriers.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

@Entity
@Table(name = "CompanyMaster", schema = "dbo")
public class CompanyMaster {
    
    @Id
    @Column(name = "Code", nullable = false)
    private Long code;

    @Column(name = "Address")
    private String address;

    @Column(name = "ContactNo")
    private String contactNo;

    @Column(name = "GST")
    private String gstNo;

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getGstNo() {
        return gstNo;
    }

    public void setGstNo(String gstNo) {
        this.gstNo = gstNo;
    }
}
