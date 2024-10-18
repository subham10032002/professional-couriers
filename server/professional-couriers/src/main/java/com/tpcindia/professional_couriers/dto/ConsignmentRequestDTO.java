package com.tpcindia.professional_couriers.dto;

public class ConsignmentRequestDTO {
    private String branch;
    private String custCode;

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getCustCode() {
        return custCode;
    }

    public void setCustCode(String code) {
        this.custCode = code;
    }
}
