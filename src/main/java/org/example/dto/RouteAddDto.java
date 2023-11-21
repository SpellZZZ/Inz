package org.example.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@Getter
@Setter
public class RouteAddDto implements Serializable {

    private static final long serialVersionUID = 5926468583005150707L;

    private String date;
    private String name;
    private String zipCode;
    private String houseNumber;
    private String gpsX;
    private String gpsY;
    private String address;
    private String city;
    private String driver;
    private String description;


    public RouteAddDto(String date, String name, String zipCode, String houseNumber, String gpsX, String gpsY, String address, String city, String driver, String description) {

        this.date = date;
        this.name = name;
        this.zipCode = zipCode;
        this.houseNumber = houseNumber;
        this.gpsX = gpsX;
        this.gpsY = gpsY;
        this.address = address;
        this.city = city;
        this.driver = driver;
        this.description = description;
    }

    public RouteAddDto() {
    }
}
