package org.example.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class CommissionDto implements Serializable {

    private static final long serialVersionUID = 5926468583005150707L;


    private String zip_codestart;
    private String house_numberstart;
    private Double gps_xstart;
    private Double gps_ystart;
    private String addressstart;
    private String citystart;
    private String zip_codeend;
    private String house_numberend;
    private Double gps_xend;
    private Double gps_yend;
    private String addressend;
    private String cityend;


    private String description;
    private Double xpackage;
    private Double ypackage;
    private Double zpackage;
    private Double mass;
    private Boolean stackable;
    private int count;

    public CommissionDto() {
    }

    public CommissionDto(String zip_codestart, String house_numberstart, Double gps_xstart, Double gps_ystart, String addressstart, String citystart, String zip_codeend, String house_numberend, Double gps_xend, Double gps_yend, String addressend, String cityend, String description, Double xpackage, Double ypackage, Double zpackage, Double mass, Boolean stackable, int count) {
        this.zip_codestart = zip_codestart;
        this.house_numberstart = house_numberstart;
        this.gps_xstart = gps_xstart;
        this.gps_ystart = gps_ystart;
        this.addressstart = addressstart;
        this.citystart = citystart;
        this.zip_codeend = zip_codeend;
        this.house_numberend = house_numberend;
        this.gps_xend = gps_xend;
        this.gps_yend = gps_yend;
        this.addressend = addressend;
        this.cityend = cityend;
        this.description = description;
        this.xpackage = xpackage;
        this.ypackage = ypackage;
        this.zpackage = zpackage;
        this.mass = mass;
        this.stackable = stackable;
        this.count = count;
    }
}