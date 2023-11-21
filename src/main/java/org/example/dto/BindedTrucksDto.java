package org.example.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class BindedTrucksDto implements Serializable {


    private static final long serialVersionUID = 5926468583005150707L;

    private int truck_id;
    private int trailer_id;


    private String truckModel;
    private String truckReg;
    private String trailerDesc;
    private String toString;



    public BindedTrucksDto() {
    }

    public BindedTrucksDto(int truck_id, int trailer_id, String truckModel, String truckReg, String trailerDesc, String toString) {
        this.truck_id = truck_id;
        this.trailer_id = trailer_id;
        this.truckModel = truckModel;
        this.truckReg = truckReg;
        this.trailerDesc = trailerDesc;
        this.toString = toString;
    }
}
