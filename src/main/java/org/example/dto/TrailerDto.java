package org.example.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class TrailerDto implements Serializable {

    private static final long serialVersionUID = 5926468583005150707L;


    private  double width;
    private  double height;
    private  double volume;
    private  int maxMass;
    private  int mass;
    private  boolean isDismount;
    private  String licensePlate;

    private  String description;


    public TrailerDto() {
    }


}
