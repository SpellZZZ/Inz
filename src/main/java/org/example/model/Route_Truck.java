package org.example.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;


@Entity
@Table(name = "ROUTE_TRUCK")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Route_Truck {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "truck_id")
    @JsonBackReference
    private Truck truck_id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user_id;

    @ManyToOne
    @JoinColumn(name = "route_id")
    @JsonBackReference
    private Route route_id;

    @ManyToOne
    @JoinColumn(name = "trailer_id")
    @JsonBackReference
    private Trailer trailer_id;





}