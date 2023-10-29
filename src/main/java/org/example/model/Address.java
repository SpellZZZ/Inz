package org.example.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="ADDRESS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int address_id;

    @Column(length = 6)
    private String zip_code;
    @Column(length = 10)
    private String house_number;
    @Column(length = 10)
    private String GPS_X;
    @Column(length = 10)
    private String GPS_Y;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "street_id")
    private Street role;


    @ManyToMany(mappedBy = "addresses")
    private Set<Route> routes = new HashSet<>();

}
