package org.example.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="TRAILER")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Trailer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int trailer_id;


    private double x;
    private double y;
    private double z;
    private boolean is_detachable;
    private int max_payload;
    private String registration_number;
    private int trailer_mass;
    @Column(length = 100)
    private String description;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id")
    private Company company;

    @ManyToMany(mappedBy = "trailers")
    @JsonBackReference
    private Set<Truck> trucks = new HashSet<>();
}

