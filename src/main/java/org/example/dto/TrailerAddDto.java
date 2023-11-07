package org.example.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class TrailerAddDto implements Serializable {

    private static final long serialVersionUID = 5926468583005150707L;


    private  String token;
    private  int width;
    private  int height;
    private  int volume;
    private  int maxMass;
    private  int mass;
    private  boolean dismount;


    public TrailerAddDto() {
    }


    public TrailerAddDto(String token, int width, int height, int volume, int maxMass, int mass, boolean dismount) {
        this.token = token;
        this.width = width;
        this.height = height;
        this.volume = volume;
        this.maxMass = maxMass;
        this.mass = mass;
        this.dismount = dismount;
    }
}
