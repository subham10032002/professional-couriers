//package com.tpcindia.professional_couriers.webcode;
//
///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package Bean;
//
//import java.util.*;
//
///**
// *
// * @author biju
// */
//public class AccountsCustomer {
//
//    private String custName, custAddr, custCntNo, custEmail, custUrl, branch, type, doj, flag, custFind, route, tpcIndiaCode,
//            StaffName, masterCompany, vendorAllotedCode, gstin, typeofTax, placeofCustomer, misRequired, operationcode,
//            quot_Dox,quot_NonDox ,tpcreffdata="No",tpcpassw=""  ;
//    private int code, ledgercode, creditPeriod, branchCode, staffCode, minBillValue, priority = 1, masterCompanyCode;
//    private Date conDate;
//    private Double stax = 0.00, fuelCharge = 0.00, tax1 = 0.00, tax2 = 0.00;
//
//    public AccountsCustomer() {
//    }
//
//    public AccountsCustomer(int cod, String name, String addr, String cntno, String email, String url, String branc,
//                            String typ, String doj, String fla, int ledgCod, int creditPer, int staffcod, int priorit, String rout,
//                            int minBill, String tpci, int mastcomp,String vendAllco, String gsti,String typofta,String placeofcu,
//                            String misreq,String optcode,String quodox,String quonondo,String tpcpass,String tpcreff) {
//        //default
//        this.code = cod;
//        this.custName = name.trim();
//        this.custAddr = addr.trim();
//        this.custCntNo = cntno;
//        this.custEmail = email.trim();
//        this.custUrl = url;
//        this.branch = branc==null?"":branc.trim();
//        this.type = typ.trim();
//        this.doj = doj;
//        this.flag = fla.trim();
//        this.ledgercode = ledgCod;
//        this.creditPeriod = creditPer;
//        this.staffCode = staffcod;
//        this.priority = priorit;
//        this.route = rout.trim();
//        this.minBillValue = minBill;
//        this.tpcIndiaCode = tpci.trim();
//        this.masterCompanyCode = mastcomp;
//        this.vendorAllotedCode=vendAllco;
//        this.gstin=gsti;
//        this.typeofTax=typofta;
//        this.placeofCustomer=placeofcu;
//        this.misRequired=misreq;
//        this.operationcode=optcode;
//        this.quot_Dox=quodox;
//        this.quot_NonDox=quonondo;
//        this.tpcpassw=tpcpass;
//        this.tpcreffdata=tpcreff;
//
//
//    }
//
//    public AccountsCustomer(int cod, String name, String addr, String cntno, String email, String url, String branc,
//                            String typ, String doj, String fla, int ledgCod, int creditPer, int staffcod, int priorit, String rout,
//                            int minBill, String tpci, int mastcomp, String vendAllco, String gsti,String typofta,String placeofcu,
//                            String misreq,String optcode,String quodox,String quonondo,String tpcpass,String tpcreff,String staf, String mastecom) {
//        this.code = cod;
//        this.custName = (name==null)?" ":name.trim();
//        this.custAddr = (addr==null)?" ":addr.trim();
//        this.custCntNo = cntno;
//        this.custEmail = (email==null)?" ":email.trim();
//        this.custUrl = url;
//        //this.branch = branc?"null":"";
//        this.branch=(branc==null)?" ":branc.trim();
//        this.type = (typ==null)?" ":typ.trim();
//        this.doj = doj;
//        this.flag = (fla==null)?" ":fla.trim();
//        this.ledgercode = ledgCod;
//        this.creditPeriod = creditPer;
//        this.staffCode = staffcod;
//        this.priority = priorit;
//        this.route = (rout==null)?" ":rout.trim();
//        this.minBillValue = minBill;
//        this.tpcIndiaCode =(tpci==null)?" ":tpci.trim();
//        this.masterCompanyCode = mastcomp;
//        this.StaffName = staf;
//        this.masterCompany = mastecom;
//        this.vendorAllotedCode=vendAllco;
//        this.gstin=gsti;
//        this.typeofTax=(typofta==null)?" ":typofta.trim();
//        this.placeofCustomer=(placeofcu==null)?" ":placeofcu.trim();
//        this.quot_Dox=(quodox==null)?" ":quodox.trim();
//        this.quot_NonDox=(quonondo==null)?" ":quonondo.trim();
//        this.misRequired=misreq;
//        this.operationcode=optcode;
//        this.tpcpassw=tpcpass;
//        this.tpcreffdata=tpcreff;
//    }
//
//    public AccountsCustomer(int cod, String name, String addr, String cntno, String email, String url, String branc,
//                            String typ, String doj, String fla, int ledgCod, int creditPer, int staffcod, int priorit, String rout,
//                            int minBill, String tpci, int mastcomp,String vendAllco, String gsti,String typofta,String placeofcu,
//                            String misreq,String optcode,String quodox,String quonondo,String tpcpass,String tpcreff,String mastcom) {
//        //master_ledger
//        this.code = cod;
//        this.custName = name.trim();
//        this.custAddr = addr.trim();
//        this.custCntNo = cntno;
//        this.custEmail = email.trim();
//        this.custUrl = url;
//        this.branch = branc==null?"":branc.trim();
//        this.type = typ.trim();
//        this.doj = doj;
//        this.flag = fla.trim();
//        this.ledgercode = ledgCod;
//        this.creditPeriod = creditPer;
//        this.staffCode = staffcod;
//        this.priority = priorit;
//        this.route = rout.trim();
//        this.minBillValue = minBill;
//        this.tpcIndiaCode = tpci.trim();
//        this.masterCompanyCode = mastcomp;
//        this.vendorAllotedCode=vendAllco;
//        this.gstin=gsti;
//        this.typeofTax=typofta;
//        this.placeofCustomer=placeofcu;
//        this.misRequired=misreq;
//        this.operationcode=optcode;
//        this.quot_Dox=quodox;
//        this.quot_NonDox=quonondo;
//        this.tpcpassw=tpcpass;
//        this.tpcreffdata=tpcreff;
//        this.masterCompany=mastcom;
//
//
//    }
//
//    /**
//     * @return the custName
//     */
//    public String getCustName() {
//        return custName;
//    }
//
//    /**
//     * @param custName the custName to set
//     */
//    public void setCustName(String custName) {
//        this.custName = custName;
//    }
//
//    /**
//     * @return the custAddr
//     */
//    public String getCustAddr() {
//        return custAddr;
//    }
//
//    /**
//     * @param custAddr the custAddr to set
//     */
//    public void setCustAddr(String custAddr) {
//        this.custAddr = custAddr;
//    }
//
//    /**
//     * @return the custCntNo
//     */
//    public String getCustCntNo() {
//        return custCntNo;
//    }
//
//    /**
//     * @param custCntNo the custCntNo to set
//     */
//    public void setCustCntNo(String custCntNo) {
//        this.custCntNo = custCntNo;
//    }
//
//    /**
//     * @return the custEmail
//     */
//    public String getCustEmail() {
//        return custEmail;
//    }
//
//    /**
//     * @param custEmail the custEmail to set
//     */
//    public void setCustEmail(String custEmail) {
//        this.custEmail = custEmail;
//    }
//
//    /**
//     * @return the custUrl
//     */
//    public String getCustUrl() {
//        return custUrl;
//    }
//
//    /**
//     * @param custUrl the custUrl to set
//     */
//    public void setCustUrl(String custUrl) {
//        this.custUrl = custUrl;
//    }
//
//    /**
//     * @return the branch
//     */
//    public String getBranch() {
//        return branch;
//    }
//
//    /**
//     * @param branch the branch to set
//     */
//    public void setBranch(String branch) {
//        this.branch = branch;
//    }
//
//    /**
//     * @return the type
//     */
//    public String getType() {
//        return type;
//    }
//
//    /**
//     * @param type the type to set
//     */
//    public void setType(String type) {
//        this.type = type;
//    }
//
//    /**
//     * @return the doj
//     */
//    public String getDoj() {
//        return doj;
//    }
//
//    /**
//     * @param doj the doj to set
//     */
//    public void setDoj(String doj) {
//        this.doj = doj;
//    }
//
//    /**
//     * @return the flag
//     */
//    public String getFlag() {
//        return flag;
//    }
//
//    /**
//     * @param flag the flag to set
//     */
//    public void setFlag(String flag) {
//        this.flag = flag;
//    }
//
//    /**
//     * @return the code
//     */
//    public int getCode() {
//        return code;
//    }
//
//    /**
//     * @param code the code to set
//     */
//    public void setCode(int code) {
//        this.code = code;
//    }
//
//    /**
//     * @return the ledgercode
//     */
//    public int getLedgercode() {
//        return ledgercode;
//    }
//
//    /**
//     * @param ledgercode the ledgercode to set
//     */
//    public void setLedgercode(int ledgercode) {
//        this.ledgercode = ledgercode;
//    }
//
//    /**
//     * @return the creditPeriod
//     */
//    public int getCreditPeriod() {
//        return creditPeriod;
//    }
//
//    /**
//     * @param creditPeriod the creditPeriod to set
//     */
//    public void setCreditPeriod(int creditPeriod) {
//        this.creditPeriod = creditPeriod;
//    }
//
//    /**
//     * @return the conDate
//     */
//    public Date getConDate() {
//        return conDate;
//    }
//
//    /**
//     * @param conDate the conDate to set
//     */
//    public void setConDate(Date conDate) {
//        this.conDate = conDate;
//    }
//
//    /**
//     * @return the custFind
//     */
//    public String getCustFind() {
//        return custFind;
//    }
//
//    /**
//     * @param custFind the custFind to set
//     */
//    public void setCustFind(String custFind) {
//        this.custFind = custFind;
//    }
//
//    /**
//     * @return the branchCode
//     */
//    public int getBranchCode() {
//        return branchCode;
//    }
//
//    /**
//     * @param branchCode the branchCode to set
//     */
//    public void setBranchCode(int branchCode) {
//        this.branchCode = branchCode;
//    }
//
//    /**
//     * @return the route
//     */
//    public String getRoute() {
//        return route;
//    }
//
//    /**
//     * @param route the route to set
//     */
//    public void setRoute(String route) {
//        this.route = route;
//    }
//
//    /**
//     * @return the staffCode
//     */
//    public int getStaffCode() {
//        return staffCode;
//    }
//
//    /**
//     * @param staffCode the staffCode to set
//     */
//    public void setStaffCode(int staffCode) {
//        this.staffCode = staffCode;
//    }
//
//    /**
//     * @return the priority
//     */
//    public int getPriority() {
//        return priority;
//    }
//
//    /**
//     * @param priority the priority to set
//     */
//    public void setPriority(int priority) {
//        this.priority = priority;
//    }
//
//    /**
//     * @return the minBillValue
//     */
//    public int getMinBillValue() {
//        return minBillValue;
//    }
//
//    /**
//     * @param minBillValue the minBillValue to set
//     */
//    public void setMinBillValue(int minBillValue) {
//        this.minBillValue = minBillValue;
//    }
//
//    /**
//     * @return the tpcIndiaCode
//     */
//    public String getTpcIndiaCode() {
//        return tpcIndiaCode;
//    }
//
//    /**
//     * @param tpcIndiaCode the tpcIndiaCode to set
//     */
//    public void setTpcIndiaCode(String tpcIndiaCode) {
//        this.tpcIndiaCode = tpcIndiaCode;
//    }
//
//    /**
//     * @return the masterCompanyCode
//     */
//    public int getMasterCompanyCode() {
//        return masterCompanyCode;
//    }
//
//    /**
//     * @param masterCompanyCode the masterCompanyCode to set
//     */
//    public void setMasterCompanyCode(int masterCompanyCode) {
//        this.masterCompanyCode = masterCompanyCode;
//    }
//
//    /**
//     * @return the StaffName
//     */
//    public String getStaffName() {
//        return StaffName;
//    }
//
//    /**
//     * @param StaffName the StaffName to set
//     */
//    public void setStaffName(String StaffName) {
//        this.StaffName = StaffName;
//    }
//
//    /**
//     * @return the masterCompany
//     */
//    public String getMasterCompany() {
//        return masterCompany;
//    }
//
//    /**
//     * @param masterCompany the masterCompany to set
//     */
//    public void setMasterCompany(String masterCompany) {
//        this.masterCompany = masterCompany;
//    }
//
//    /**
//     * @return the stax
//     */
//    public Double getStax() {
//        return stax;
//    }
//
//    /**
//     * @param stax the stax to set
//     */
//    public void setStax(Double stax) {
//        this.stax = stax;
//    }
//
//    /**
//     * @return the fuelCharge
//     */
//    public Double getFuelCharge() {
//        return fuelCharge;
//    }
//
//    /**
//     * @param fuelCharge the fuelCharge to set
//     */
//    public void setFuelCharge(Double fuelCharge) {
//        this.fuelCharge = fuelCharge;
//    }
//
//    /**
//     * @return the tax1
//     */
//    public Double getTax1() {
//        return tax1;
//    }
//
//    /**
//     * @param tax1 the tax1 to set
//     */
//    public void setTax1(Double tax1) {
//        this.tax1 = tax1;
//    }
//
//    /**
//     * @return the tax2
//     */
//    public Double getTax2() {
//        return tax2;
//    }
//
//    /**
//     * @param tax2 the tax2 to set
//     */
//    public void setTax2(Double tax2) {
//        this.tax2 = tax2;
//    }
//
//    /**
//     * @return the vendorAllotedCode
//     */
//    public String getVendorAllotedCode() {
//        return vendorAllotedCode;
//    }
//
//    /**
//     * @param vendorAllotedCode the vendorAllotedCode to set
//     */
//    public void setVendorAllotedCode(String vendorAllotedCode) {
//        this.vendorAllotedCode = vendorAllotedCode;
//    }
//
//
//    public String getTypeofTax() {
//        return typeofTax;
//    }
//
//    /**
//     * @param typeofTax the typeofTax to set
//     */
//    public void setTypeofTax(String typeofTax) {
//        this.typeofTax = typeofTax;
//    }
//
//    /**
//     * @return the placeofCustomer
//     */
//    public String getPlaceofCustomer() {
//        return placeofCustomer;
//    }
//
//    /**
//     * @param placeofCustomer the placeofCustomer to set
//     */
//    public void setPlaceofCustomer(String placeofCustomer) {
//        this.placeofCustomer = placeofCustomer;
//    }
//
//    /**
//     * @return the misRequired
//     */
//    public String getMisRequired() {
//        return misRequired;
//    }
//
//    /**
//     * @param misRequired the misRequired to set
//     */
//    public void setMisRequired(String misRequired) {
//        this.misRequired = misRequired;
//    }
//
//    /**
//     * @return the operationcode
//     */
//    public String getOperationcode() {
//        return operationcode;
//    }
//
//    /**
//     * @param operationcode the operationcode to set
//     */
//    public void setOperationcode(String operationcode) {
//        this.operationcode = operationcode;
//    }
//
//    /**
//     * @return the gstin
//     */
//    public String getGstin() {
//        return gstin;
//    }
//
//    /**
//     * @param gstin the gstin to set
//     */
//    public void setGstin(String gstin) {
//        this.gstin = gstin;
//    }
//
//    /**
//     * @return the quot_Dox
//     */
//    public String getQuot_Dox() {
//        return quot_Dox;
//    }
//
//    /**
//     * @param quot_Dox the quot_Dox to set
//     */
//    public void setQuot_Dox(String quot_Dox) {
//        this.quot_Dox = quot_Dox;
//    }
//
//    /**
//     * @return the quot_NonDox
//     */
//    public String getQuot_NonDox() {
//        return quot_NonDox;
//    }
//
//    /**
//     * @param quot_NonDox the quot_NonDox to set
//     */
//    public void setQuot_NonDox(String quot_NonDox) {
//        this.quot_NonDox = quot_NonDox;
//    }
//
//    /**
//     * @return the tpcreffdata
//     */
//    public String getTpcreffdata() {
//        return tpcreffdata;
//    }
//
//    /**
//     * @param tpcreffdata the tpcreffdata to set
//     */
//    public void setTpcreffdata(String tpcreffdata) {
//        this.tpcreffdata = tpcreffdata;
//    }
//
//    /**
//     * @return the tpcpassw
//     */
//    public String getTpcpassw() {
//        return tpcpassw;
//    }
//
//    /**
//     * @param tpcpassw the tpcpassw to set
//     */
//    public void setTpcpassw(String tpcpassw) {
//        this.tpcpassw = tpcpassw;
//    }
//}
//
