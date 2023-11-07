package org.example.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class TruckAddDto implements Serializable {

    private static final long serialVersionUID = 5926468583005150707L;


    private  String token;
    private  String model;
    private  int mass;
    private  String licensePlate;

    public TruckAddDto() {
    }


    public TruckAddDto(String token, String model, int mass, String licensePlate) {
        this.token = token;
        this.model = model;
        this.mass = mass;
        this.licensePlate = licensePlate;
    }
}
