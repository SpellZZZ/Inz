package org.example.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="USER")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int user_id;
    @Column(unique = true, length = 15)
    String username;
    @Column(length = 128)
    String password;
    @Column(length = 30)
    @Email
    String email;


    @Column(length = 30)
    @Email
    String name;

    @Column(length = 50)
    @Email
    String surname;

    Boolean deleted;
    @Column(length = 10)
    String password_recovery_link;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id")
    private Company company;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "User_Truck",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "truck_id"))
    private Set<Truck> trucks = new HashSet<>();


}
