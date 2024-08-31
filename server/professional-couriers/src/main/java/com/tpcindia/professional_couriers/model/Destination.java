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

    @Column(name = "Destn")
    private String destn;

    @Column(name = "AreaCode")
    private String areaCode;

    @Column(name = "Hub")
    private String hub;

    @Column(name = "State")
    private String state;

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

    public String getDestn() {
        return destn;
    }

    public void setDestn(String destn) {
        this.destn = destn;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getHub() {
        return hub;
    }

    public void setHub(String hub) {
        this.hub = hub;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

}
