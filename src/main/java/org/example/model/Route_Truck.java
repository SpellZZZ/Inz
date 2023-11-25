package org.example.model;

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
    private Truck truck_id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user_id;

    @ManyToOne
    @JoinColumn(name = "route_id")
    private Route route_id;

    @ManyToOne
    @JoinColumn(name = "trailer_id")
    private Trailer trailer_id;





}