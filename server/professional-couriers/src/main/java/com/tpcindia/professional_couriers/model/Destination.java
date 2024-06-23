package com.tpcindia.professional_couriers.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Destin", schema = "dbo")
public class Destination {

    @Id
    @Column(name = "DestCode", nullable = false)
    private String destCode;

    @Column(name = "Pincode")
    private String pincode;

    @Column(name = "City")
    private String city;

    public String getDestCode() {
        return destCode;
    }

    public void setDestCode(String destCode) {
        this.destCode = destCode;
    }

    public String getPinCode() {
        return pincode;
    }

    public void setPinCode(String pinCode) {
        this.pincode = pinCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

}
