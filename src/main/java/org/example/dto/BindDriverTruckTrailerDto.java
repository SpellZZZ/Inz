package org.example.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


@Getter
@Setter
public class BindDriverTruckTrailerDto implements Serializable {

    private static final long serialVersionUID = 5926468583005150707L;


    private int user_id;
    private int truck_id;
    private int trailer_id;

    public BindDriverTruckTrailerDto() {
    }


    public BindDriverTruckTrailerDto(int user_id, int truck_id, int trailer_id) {
        this.user_id = user_id;
        this.truck_id = truck_id;
        this.trailer_id = trailer_id;
    }
}
