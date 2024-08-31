package com.tpcindia.professional_couriers.dto;

public class EmailDTO {

    private String branch;
    private String userName;
    private String branchCode;
    private String usercode;

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getUsercode() {
        return usercode;
    }

    public void setUsercode(String code) {
        this.usercode = code;
    }
}
