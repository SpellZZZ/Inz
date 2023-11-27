package org.example.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


@Getter
@Setter
public class RouteHistoryDto implements Serializable {
    private static final long serialVersionUID = 5926468583005150707L;
    private int id;
    private String truck;
    private String data_start;
    private String data_end;
}
