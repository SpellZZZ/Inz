package org.example.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class DriversDto implements Serializable {

    private static final long serialVersionUID = 5926468583005150707L;


    private  String name;
    private  String surname;
    private  String truck;
    private  String trailer;



    public DriversDto() {
    }

    public DriversDto(String name, String surname, String truck, String trailer) {
        this.name = name;
        this.surname = surname;
        this.truck = truck;
        this.trailer = trailer;
    }
}