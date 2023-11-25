package org.example.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="TRUCK")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Truck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int truck_id;
    private int truck_mass;

    @Column(length = 12)
    private String registration_number;
    @Column(length = 12)
    private String vin;

    @Column(length = 100)
    private String description;

    @Column(length = 30)
    private String model;
    @Column(length = 30)
    private String brand;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id")
    private Company company;


    @ManyToMany(mappedBy = "trucks")
    @JsonBackReference
    private Set<User> users = new HashSet<>();



    @ManyToMany(mappedBy = "routeTrucks")
    private Set<Route> routeTrucks = new HashSet<>();



    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "Truck_Trailer",
            joinColumns = @JoinColumn(name = "truck_id"),
            inverseJoinColumns = @JoinColumn(name = "trailer_id"))
    @JsonManagedReference
    private Set<Trailer> trailers = new HashSet<>();



}
