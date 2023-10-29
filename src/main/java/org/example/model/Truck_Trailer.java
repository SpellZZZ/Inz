package org.example.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;


@Entity
@Table(name = "TRUCK_TRAILER")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Truck_Trailer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    private Date time_start;
    private Date time_end;



    @ManyToOne
    @JoinColumn(name = "truck_id")
    private Truck truck;

    @ManyToOne
    @JoinColumn(name = "trailer_id")
    private Trailer trailer;



}