package com.tpcindia.professional_couriers.model;

import jakarta.persistence.*;

@Entity
@Table(name = "BookAllot", schema = "dbo")
public class BookAllot {

    @Id
    @Column(name = "Bookallotcode", nullable = false)
    private Long bookAllotCode;

    @Column(name = "AccCode")
    private String accCode;

    @Column(name = "StartNo")
    private Long startNo;

    @Column(name = "EndNo")
    private Long endNo;

    @Column(name = "Type")
    private String type;

    @Column(name = "BranchCode")
    private String branchCode;


}
