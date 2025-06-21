package com.tpcindia.professional_couriers.model;

import jakarta.persistence.*;

@Entity
@Table(name = "CreditBookingData", schema = "dbo")
public class CreditBookingData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "currentDate", nullable = false)
    private String currentDate;

    @Column(name = "consignmentNumber", nullable = false)
    private String consignmentNumber;

    @Column(name = "balanceStock", nullable = false)
    private String balanceStock;

    @Column(name = "clientName", nullable = false)
    private String clientName;

    @Column(name = "bookingDate", nullable = false)
    private String bookingDate;

    @Column(name = "pincode", nullable = false)
    private String pincode;

    @Column(name = "destination", nullable = false)
    private String destination;

    @Column(name = "consigneeType", nullable = false)
    private String consigneeType;

    @Column(name = "mode", nullable = false)
    private String mode;

    @Column(name = "consigneeName", nullable = false)
    private String consigneeName;

    @Column(name = "noOfPsc", nullable = false)
    private String noOfPsc;

    @Column(name = "unit", nullable = false)
    private String unit;

    @Column(name = "weight")
    private String weight;

    @Column(name = "length")
    private String length;

    @Column(name = "width")
    private String width;

    @Column(name = "height")
    private String height;

    @Column(name = "invoiceNumber")
    private String invoiceNumber;

    @Column(name = "product")
    private String product;

    @Column(name = "declaredValue")
    private String declaredValue;

    @Column(name = "ewayBill")
    private String ewayBill;

    @Column(name = "branch")
    private String branch;

    @Column(name = "custCode")
    private String custCode;

    @Column(name = "emailSent")
    private String emailSent;

    @Column(name = "dateOfEmailSent")
    private String dateOfEmailSent;

    @Column(name = "userName")
    private String userName;

    @Column(name = "emailSenderUsername")
    private String emailSenderUsername;

    @Column(name = "sync")
    private String sync;

    @Column(name = "userCode")
    private String userCode;

    @Column(name = "longitude")
    private String longitude;

    @Column(name = "latitude")
    private String latitude;

    @Column(name = "destcode")
    private String destCode;

    @Column(name = "TransactionID")
    private String transactionId;

    @Lob
    @Column(name = "photoOfAddress")
    private byte[] photoOfAddress;

    @Lob
    @Column(name = "pdfAddress")
    private byte[] pdfAddress;

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public String getConsignmentNumber() {
        return consignmentNumber;
    }

    public void setConsignmentNumber(String consignmentNumber) {
        this.consignmentNumber = consignmentNumber;
    }

    public String getBalanceStock() {
        return balanceStock;
    }

    public void setBalanceStock(String balanceStock) {
        this.balanceStock = balanceStock;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getConsigneeType() {
        return consigneeType;
    }

    public void setConsigneeType(String consigneeType) {
        this.consigneeType = consigneeType;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getConsigneeName() {
        return consigneeName;
    }

    public void setConsigneeName(String consigneeName) {
        this.consigneeName = consigneeName;
    }

    public String getNoOfPsc() {
        return noOfPsc;
    }

    public void setNoOfPsc(String noOfPsc) {
        this.noOfPsc = noOfPsc;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getDeclaredValue() {
        return declaredValue;
    }

    public void setDeclaredValue(String declaredValue) {
        this.declaredValue = declaredValue;
    }

    public String getEwayBill() {
        return ewayBill;
    }

    public void setEwayBill(String ewayBill) {
        this.ewayBill = ewayBill;
    }

    public byte[] getPhotoOfAddress() {
        return photoOfAddress;
    }
    public void setPhotoOfAddress(byte[] photoOfAddress) {
        this.photoOfAddress = photoOfAddress;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getCustCode() {
        return custCode;
    }

    public void setCustCode(String custCode) {
        this.custCode = custCode;
    }

    public String getDateOfEmailSent() {
        return dateOfEmailSent;
    }

    public void setDateOfEmailSent(String dateOfEmailSent) {
        this.dateOfEmailSent = dateOfEmailSent;
    }

    public String getEmailSent() {
        return emailSent;
    }

    public void setEmailSent(String emailSent) {
        this.emailSent = emailSent;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmailSenderUserName() {
        return emailSenderUsername;
    }

    public void setEmailSenderUsername(String userName) {
        this.emailSenderUsername = userName;
    }

    public String getSync() {
        return sync;
    }

    public void setSync(String sync) {
        this.sync = sync;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String code) {
        this.userCode = code;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getDestCode() {
        return destCode;
    }

    public void setDestCode(String code) {
        this.destCode = code;
    }

    public byte[] getPdfAddress() {
        return pdfAddress;
    }

    public void setPdfAddress(byte[] address) {
        this.pdfAddress = address;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String id) {
        this.transactionId = id;
    }
}
