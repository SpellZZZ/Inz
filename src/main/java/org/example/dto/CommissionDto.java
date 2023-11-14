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
    private String GPS_XStart;
    private String GPS_YStart;
    private String addressStart;
    private String zip_codeEnd;
    private String house_numberEnd;
    private String GPS_XEnd;
    private String GPS_YEnd;
    private String addressEnd;


    private String description;
    private Double X;
    private Double Y;
    private Double Z;
    private Double mass;
    private Boolean stackable;
    private int count;

    public CommissionDto() {
    }

    public CommissionDto(String zip_codeStart, String house_numberStart, String GPS_XStart, String GPS_YStart, String addressStart, String zip_codeEnd, String house_numberEnd, String GPS_XEnd, String GPS_YEnd, String addressEnd, String description, Double x, Double y, Double z, Double mass, Boolean stackable, int count) {
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
        this.X = x;
        this.Y = y;
        this.Z = z;
        this.mass = mass;
        this.stackable = stackable;
        this.count = count;
    }
}