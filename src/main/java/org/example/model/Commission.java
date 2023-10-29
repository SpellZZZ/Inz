package org.example.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Entity
@Table(name="COMMISSION")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Commission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int commission_id;

    @Column(length = 6)
    private String zip_code;



    private Date date_of_placement;
    private Date date_of_receipt;
    @Column(length = 255)
    private String description;
    private Double X;
    private Double Y;
    private Double Z;
    private Double mass;
    private Boolean stackable;
    private int count;
    private Boolean is_loaded;
    private Boolean is_unloaded;
    private int canceled;



    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "route_id")
    private Route route;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "address_pickup_id")
    private Address delivery_pickup;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "address_endpoint_id")
    private Address delivery_endpoint;



}