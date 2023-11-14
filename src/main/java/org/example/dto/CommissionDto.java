package org.example.dto;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Date;

@Getter
@Setter
public class CommissionDto implements Serializable {

    private static final long serialVersionUID = 5926468583005150707L;


    private String zip_codeStart;
    private String house_numberStart;
    private Double GPS_XStart;
    private Double GPS_YStart;
    private String addressStart;
    private String zip_codeEnd;
    private String house_numberEnd;
    private Double GPS_XEnd;
    private Double GPS_YEnd;
    private String addressEnd;


    private String description;
    private Double xPackage;
    private Double yPackage;
    private Double zPackage;
    private Double mass;
    private Boolean stackable;
    private int count;

    public CommissionDto() {
    }

    public CommissionDto(String zip_codeStart, String house_numberStart, Double GPS_XStart, Double GPS_YStart, String addressStart, String zip_codeEnd, String house_numberEnd, Double GPS_XEnd, Double GPS_YEnd, String addressEnd, String description, Double xPackage, Double yPackage, Double zPackage, Double mass, Boolean stackable, int count) {
        this.zip_codeStart = zip_codeStart;
        this.house_numberStart = house_numberStart;
        this.GPS_XStart = GPS_XStart;
        this.GPS_YStart = GPS_YStart;
        this.addressStart = addressStart;
        this.zip_codeEnd = zip_codeEnd;
        this.house_numberEnd = house_numberEnd;
        this.GPS_XEnd = GPS_XEnd;
        this.GPS_YEnd = GPS_YEnd;
        this.addressEnd = addressEnd;
        this.description = description;
        this.xPackage = xPackage;
        this.yPackage = yPackage;
        this.zPackage = zPackage;
        this.mass = mass;
        this.stackable = stackable;
        this.count = count;
    }
}