package com.tpcindia.professional_couriers.dto.responsedto;

public class FirmDetailsDTO {
    private String firmName;
    private String address;
    private String contactNo;

    public FirmDetailsDTO(String firmName, String address, String contactNo) {
        this.firmName = firmName;
        this.address = address;
        this.contactNo = contactNo;
    }

    public String getFirmName() {
        return firmName;
    }

    public String getAddress() {
        return address;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setFirmName(String name) {
        this.firmName = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setContactNo(String number) {
        this.contactNo = number;
    }
}
