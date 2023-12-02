package org.example.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@Getter
@Setter
public class RouteAddDto implements Serializable {

    private static final long serialVersionUID = 5926468583005150707L;

    private String date;
    private String datePredict;
    private String name;
    private String zipCode;
    private String houseNumber;
    private String gpsX;
    private String gpsY;
    private String address;
    private String city;


    private String nameEnd;
    private String zipCodeEnd;
    private String houseNumberEnd;
    private String gpsXEnd;
    private String gpsYEnd;
    private String addressEnd;
    private String cityEnd;

    private String driver;
    private String description;

    public RouteAddDto(String date,String datePredict, String name, String zipCode, String houseNumber, String gpsX, String gpsY, String address, String city, String nameEnd, String zipCodeEnd, String houseNumberEnd, String gpsXEnd, String gpsYEnd, String addressEnd, String cityEnd, String driver, String description) {
        this.date = date;
        this.name = name;
        this.zipCode = zipCode;
        this.houseNumber = houseNumber;
        this.gpsX = gpsX;
        this.gpsY = gpsY;
        this.address = address;
        this.city = city;
        this.nameEnd = nameEnd;
        this.zipCodeEnd = zipCodeEnd;
        this.houseNumberEnd = houseNumberEnd;
        this.gpsXEnd = gpsXEnd;
        this.gpsYEnd = gpsYEnd;
        this.addressEnd = addressEnd;
        this.cityEnd = cityEnd;
        this.driver = driver;
        this.description = description;
        this.datePredict = datePredict;
    }

    public RouteAddDto() {
    }
}
