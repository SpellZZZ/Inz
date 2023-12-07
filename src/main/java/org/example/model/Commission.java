package org.example.model;

import com.fasterxml.jackson.annotation.*;
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
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "commission_id")
public class Commission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int commission_id;


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
    private Boolean is_selected;
    private int canceled;
    private int point_number_start;
    private int point_number_end;



    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "route_id")
    @JsonIgnore
    private Route route;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "address_pickup_id")
    @JsonIgnore
    private Address delivery_pickup;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "address_endpoint_id")
    @JsonIgnore
    private Address delivery_endpoint;



}
