package org.example.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Entity
@Table(name = "USER_TRUCK")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User_Truck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int user_truck_id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "truck_id")
    private Truck truck;

    private Date date_start;
    private Date date_end;



}
