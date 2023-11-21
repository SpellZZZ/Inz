package org.example.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.model.Commission;

import java.io.Serializable;
import java.util.List;


    @Getter
    @Setter
    public class RouteDataDto implements Serializable {

        private static final long serialVersionUID = 5926468583005150707L;

        private int route_id;
        private String truckModel;
        private String truckReg;
        private String trailerDesc;
        private String name;


        private String date;
        private String zipCode;
        private String houseNumber;
        private double gpsX;
        private double gpsY;
        private String address;
        private String city;
        private String driver;
        private String description;

        private List<Commission> packages;


        public RouteDataDto() {
        }

        public RouteDataDto(int route_id, String truckModel, String truckReg, String trailerDesc, String date, String name, String zipCode, String houseNumber, double gpsX, double gpsY, String address, String city, String driver, String description, List<Commission> packages) {
            this.route_id = route_id;
            this.truckModel = truckModel;
            this.truckReg = truckReg;
            this.trailerDesc = trailerDesc;
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
            this.packages = packages;
        }
    }
