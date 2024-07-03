package com.tpcindia.professional_couriers.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Bookmaster", schema = "dbo")
public class BookMaster {

    @Id
    @Column(name = "BookNo", nullable = false)
    private Long bookNo;

    @Column(name = "BookType")
    private String bookType;

}
