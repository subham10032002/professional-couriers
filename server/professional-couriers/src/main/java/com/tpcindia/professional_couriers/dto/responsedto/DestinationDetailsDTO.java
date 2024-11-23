package com.tpcindia.professional_couriers.dto.responsedto;

public class DestinationDetailsDTO {
    private String destCode;

    private String city;

    private String destn;

    private String areaCode;

    private String hub;

    private String state;

    private String pdfCity;

    public String getDestCode() {
        return destCode;
    }

    public void setDestCode(String destCode) {
        this.destCode = destCode;
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

    public String getPdfCity() {
        return pdfCity;
    }

    public void setPdfCity(String pdfCity) {
        this.pdfCity = pdfCity;
    }
}
