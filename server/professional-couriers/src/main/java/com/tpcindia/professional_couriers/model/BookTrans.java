package com.tpcindia.professional_couriers.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Booktrans", schema = "dbo")
public class BookTrans {

    @Id
    @Column(name = "BookNo", nullable = false)
    private Long bookNo;

    @Column(name = "AccNo")
    private Long accNo;

    @Column(name = "Counter")
    private String counter;


}
