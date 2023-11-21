package org.example.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@Getter
@Setter
public class RouteAddDto implements Serializable {

    private static final long serialVersionUID = 5926468583005150707L;


    private int user_id;
    private int truck_id;
    private String date_start;
    private String date_end;


    public RouteAddDto(int user_id, int truck_id, String date_start, String date_end) {
        this.user_id = user_id;
        this.truck_id = truck_id;
        this.date_start = date_start;
        this.date_end = date_end;
    }

    public RouteAddDto() {
    }
}
