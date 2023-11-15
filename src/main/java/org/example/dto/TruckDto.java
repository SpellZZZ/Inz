package org.example.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class TruckDto implements Serializable {

    private static final long serialVersionUID = 5926468583005150707L;



    private  String model;
    private  int mass;
    private  String licensePlate;

    public TruckDto() {
    }


    public TruckDto(String model, int mass, String licensePlate) {
        this.model = model;
        this.mass = mass;
        this.licensePlate = licensePlate;

    }
}
