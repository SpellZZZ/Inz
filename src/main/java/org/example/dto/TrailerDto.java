package org.example.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class TrailerDto implements Serializable {

    private static final long serialVersionUID = 5926468583005150707L;


    private  String token;
    private  double width;
    private  double height;
    private  double volume;
    private  int maxMass;
    private  int mass;
    private  boolean dismount;


    public TrailerDto() {
    }


    public TrailerDto(String token, double width, double height, double volume, int maxMass, int mass, boolean dismount) {
        this.token = token;
        this.width = width;
        this.height = height;
        this.volume = volume;
        this.maxMass = maxMass;
        this.mass = mass;
        this.dismount = dismount;
    }
}
