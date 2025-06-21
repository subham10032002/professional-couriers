package com.tpcindia.professional_couriers.dto.responsedto;

public class TopPDFDetailsDTO {
    private String transactionId;
    private String consignmentNumber;
    private String pdfAddress;

    public TopPDFDetailsDTO(String transactionId, String consignmentNumber, String pdfAddress) {
        this.transactionId = transactionId;
        this.consignmentNumber = consignmentNumber;
        this.pdfAddress = pdfAddress;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getConsignmentNumber() {
        return consignmentNumber;
    }

    public void setConsignmentNumber(String consignmentNumber) {
        this.consignmentNumber = consignmentNumber;
    }

    public String getPdfAddress() {
        return pdfAddress;
    }

    public void setPdfAddress(String pdfAddress) {
        this.pdfAddress = pdfAddress;
    }
}
